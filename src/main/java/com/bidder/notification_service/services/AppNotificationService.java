/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import com.bidder.notification_service.mappers.NotificationMapper;
import com.bidder.notification_service.repositories.NotificationRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ContactType;
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.SendNotificationResponse;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppNotificationService implements Notifier {

	private static final String APP_USER = "app-user";

	private final NotificationRepository notificationRepository;

	@Override
	public SendNotificationResponse send(@Valid SendNotificationRequest request) {
		var notification = NotificationMapper.requestToEntity(request, ContactType.APP, APP_USER);
		notificationRepository.save(notification);
		log.info("App Notification sent to {}", request.recipientId());
		return new SendNotificationResponse(NotificationStatus.SENT, ContactType.APP, request.recipientId());
	}

	public void logNotification(@Valid SendNotificationRequest request, ContactType contactType,
			String recipientContact) {
		var notification = NotificationMapper.requestToEntity(request, contactType, recipientContact);
		notificationRepository.save(notification);
		log.info("{} Notification logged for recipient {}", contactType, request.recipientId());
	}
}
