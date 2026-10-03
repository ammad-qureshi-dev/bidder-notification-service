/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import java.io.IOException;
import java.util.*;

import javax.naming.directory.NoSuchAttributeException;

import com.bidder.notification_service.external.services.AuthAndIdentityService;
import com.bidder.notification_service.mappers.NotificationMapper;
import com.bidder.notification_service.repositories.NotificationRepository;
import dtos.response.AppUserDto;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ContactType;
import models.NotificationStatus;
import models.dtos.request.NotifyRequest;
import models.dtos.response.NotificationResponseDto;
import models.dtos.response.PageResponse;
import models.dtos.response.SendNotificationResponse;
import models.entities.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.bidder.notification_service.utils.Constants.APP_USER;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private final EmailService emailService;
	private final MobileService mobileService;
	private final AppNotificationService appNotificationService;
	private final AuthAndIdentityService authAndIdentityService;
	private final NotificationRepository notificationRepository;

	public List<SendNotificationResponse> send(NotifyRequest request)
			throws NoSuchAttributeException, TemplateException, MessagingException, IOException {

		var sentResponses = new ArrayList<SendNotificationResponse>();
		var appUserId = request.recipientId();

		var appUser = authAndIdentityService.getAppUser(appUserId);

		if (appUser.isEmpty()) {
			log.error("No user found with app-user-id = {}", appUserId);
		}

		prefillAppUserInfo(appUser.get(), request);

		Map<ContactType, String> preferredContacts;

		if (request.recipientConfig() != null && !request.recipientConfig().isEmpty()) {
			preferredContacts = request.recipientConfig();
		} else {
			preferredContacts = getPreferredContacts(appUser.get());
		}

		// Always send APP notification
		sentResponses.add(appNotificationService.notify(request, APP_USER));

		for (var entry : preferredContacts.entrySet()) {
			var type = entry.getKey();
			var value = entry.getValue();

			try {
				if (type == ContactType.EMAIL) {
					sentResponses.add(emailService.notify(request, value));
				} else if (type == ContactType.PHONE) {
					sentResponses.add(mobileService.notify(request, value));
				}
			} catch (Exception e) {
				log.error("Error contacting app-user with id = {} for contact-type = {}. Full request: {}", appUserId,
						type, request);
			}
		}

		return sentResponses;
	}

	public PageResponse<NotificationResponseDto> getNotifications(ContactType contactType, UUID recipientId,
			Pageable pageable) {
		var notifications = notificationRepository.findByContactTypeAndRecipientId(contactType, recipientId, pageable)
				.map(NotificationMapper::entityToResponse);

		return PageResponse.from(notifications);
	}

	public void updateNotificationStatus(NotificationStatus status, UUID notificationId) {
		var notification = getNotificationById(notificationId);
		notification.setStatus(status);
		notificationRepository.save(notification);
	}

	public Notification getNotificationById(UUID id) {
		var n = notificationRepository.findById(id);
		if (n.isEmpty()) {
			log.error("Notification with ID = {} not found", id);
			throw new NoSuchElementException("Notification not found");
		}

		return n.get();
	}

	/**
	 * Gets the app-user's preferred contacts when no recipientConfig is provided to
	 * send a notification. If no contact is found, it sends an APP-type
	 * notification to set up contact method(s)
	 * 
	 * @param appUser
	 *            app user DTO
	 * @return map pair of ContactType and it's value
	 */
	private Map<ContactType, String> getPreferredContacts(AppUserDto appUser) {
		var preferredContact = appUser.contact();

		// ToDo: break down this if-statement
		if (preferredContact != null) {
			if (preferredContact.type() != null && preferredContact.value() != null) {
				var validContactTypes = Set.of(ContactType.PHONE, ContactType.EMAIL);
				if (validContactTypes.contains(preferredContact.type())) {
					return Map.of(preferredContact.type(), preferredContact.value());
				}
			}
		}

		var allContacts = authAndIdentityService.getContactMethods(appUser.id());

		if (allContacts != null && !allContacts.isEmpty()) {
			return allContacts;
		}

		log.error("App-user with this id = {} does not have any contact methods set up", appUser.id());
		return Collections.emptyMap();
	}

	/**
	 * Retrieves user data like name and fills templateData with the information
	 * 
	 * @param request
	 */
	private void prefillAppUserInfo(AppUserDto appUser, NotifyRequest request) {
		var templateData = request.templateData();

		if (templateData == null || templateData.isEmpty() || appUser == null) {
			return;
		}

		templateData.put("fullName", appUser.fullName());
	}
}
