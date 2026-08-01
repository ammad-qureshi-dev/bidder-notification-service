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
import models.TemplateName;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.NotificationResponseDto;
import models.dtos.response.SendNotificationResponse;
import models.entities.Notification;
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

		var config = request.recipientConfig();
		var appUserId = request.recipientId();

		if (config == null || config.isEmpty()) {
			config = new HashMap<>();

			var preferredContact = authAndIdentityService.getPreferredContactType(appUserId);

			if (preferredContact != null) {
				config.put(preferredContact.getFirst(), preferredContact.getSecond());
			} else {
				var allContacts = authAndIdentityService.getContactMethods(appUserId);

				// If for some reason there is NO contact method set up for this user, send an
				// app notification requesting to set up one
				if (allContacts == null || allContacts.isEmpty()) {
					log.error("app-user {} does not have any contact methods set up", appUserId);

					appNotificationService
							.send(new SendNotificationRequest(appUserId, TemplateName.CONTACT_METHOD_SETUP, null,
									// ToDo: add setup url
									Map.of("setupUrl", "http://localhost:3000")));
				} else {
					config = allContacts;
				}
			}
		}

		var contactTypes = config.keySet();

		for (var contactType : contactTypes) {
			switch (contactType) {
				case EMAIL -> sentResponses.add(emailService.send(request));
				case PHONE -> sentResponses.add(mobileService.send(request));
				case APP -> {
					/* handled unconditionally below, not per-contact-type */ }
			}
		}

		sentResponses.add(appNotificationService.send(request));

		return sentResponses;
	}

	public List<NotificationResponseDto> getNotifications(ContactType contactType, UUID recipientId) {
		var notifications = notificationRepository.findByContactTypeAndRecipientId(contactType, recipientId);

		if (notifications == null || notifications.isEmpty()) {
			return Collections.emptyList();
		}

		return notifications.stream().map(NotificationMapper::entityToResponse).toList();
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
}
