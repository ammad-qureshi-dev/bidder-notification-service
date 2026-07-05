/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import javax.naming.directory.NoSuchAttributeException;

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

	private final NotificationRepository notificationRepository;

	public SendNotificationResponse send(@Valid SendNotificationRequest request)
			throws NoSuchAttributeException, TemplateException, MessagingException, IOException {
		var contactType = request.contactType();

		switch (contactType) {
			case EMAIL -> {
				return emailService.send(request);
			}
			case PHONE -> {
				return mobileService.send(request);
			}
			case APP -> {
				return appNotificationService.send(request);
			}
			default -> throw new NoSuchAttributeException("Contact Type does not exist");
		}
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
