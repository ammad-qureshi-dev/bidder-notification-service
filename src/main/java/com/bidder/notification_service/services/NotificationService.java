/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import java.io.IOException;
import java.util.*;

import javax.naming.directory.NoSuchAttributeException;

import com.bidder.notification_service.external.services.AuthAndIdentityService;
import com.bidder.notification_service.mappers.NotificationMapper;
import com.bidder.notification_service.repositories.NotificationRepository;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ContactType;
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.NotificationResponseDto;
import models.dtos.response.PageResponse;
import models.dtos.response.SendNotificationResponse;
import models.entities.Notification;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

	private final EmailService emailService;
	private final MobileService mobileService;
	private final AppNotificationService appNotificationService;
	private final AuthAndIdentityService authAndIdentityService;
	private final NotificationRepository notificationRepository;

	public List<SendNotificationResponse> send(@Valid SendNotificationRequest request)
			throws NoSuchAttributeException, TemplateException, MessagingException, IOException {

		var sentResponses = new ArrayList<SendNotificationResponse>();

		var recipientConfig = request.recipientConfig();
		var appUserId = request.recipientId();

		prefillAppUserInfo(request);

		if (recipientConfig == null || recipientConfig.isEmpty()) {
			recipientConfig = getPreferredContacts(appUserId, request);
		}

		// Always send APP notification
		sentResponses.add(appNotificationService.notify(request));

		var contactTypes = recipientConfig.keySet();

		for (var contactType : contactTypes) {
			try {
				if (Objects.requireNonNull(contactType) == ContactType.EMAIL) {
					sentResponses.add(emailService.notify(request));
				} else if (contactType == ContactType.PHONE) {
					sentResponses.add(mobileService.notify(request));
				}
			} catch (Exception e) {
				log.error("Error contacting app-user with id = {} for contact-type = {}. Full request: {}", appUserId,
						contactType, request);
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
	 * @param appUserId
	 *            app user id
	 * @return map pair of ContactType and it's value
	 */
	private Map<ContactType, String> getPreferredContacts(UUID appUserId, SendNotificationRequest request) {
		var preferredContact = authAndIdentityService.getPreferredContactType(appUserId);

		if (preferredContact != null) {
			request.recipientConfig().put(preferredContact.type(), preferredContact.value());
			return Map.of(preferredContact.type(), preferredContact.value());
		}

		var allContacts = authAndIdentityService.getContactMethods(appUserId);

		if (allContacts != null && !allContacts.isEmpty()) {
			request.recipientConfig().putAll(allContacts);
			return allContacts;
		}

		log.error("app-user {} does not have any contact methods set up", appUserId);
		return Collections.emptyMap();
	}

	/**
	 * Retrieves user data like name and fills templateData with the information
	 * 
	 * @param request
	 */
	private void prefillAppUserInfo(SendNotificationRequest request) {
		var templateData = request.templateData();

		if (templateData == null || templateData.isEmpty()) {
			return;
		}

		var appUser = authAndIdentityService.getAppUser(request.recipientId());

		if (appUser.isEmpty()) {
			return;
		}

		templateData.put("fullName", appUser.get().fullName());
	}
}
