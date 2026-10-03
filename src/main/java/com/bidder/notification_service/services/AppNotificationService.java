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
import models.dtos.request.NotifyRequest;
import models.dtos.response.SendNotificationResponse;
import models.entities.Notification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AppNotificationService implements Notifier {

	private final NotificationRepository notificationRepository;

	@Override
	public SendNotificationResponse notify(@Valid NotifyRequest request, String value) {
		var notification = NotificationMapper.requestToEntity(request, ContactType.APP, value);
		notificationRepository.save(notification);
		log.info("App Notification sent to {}", request.recipientId());
		return new SendNotificationResponse(NotificationStatus.SENT, ContactType.APP, request.recipientId());
	}

	public void logNotification(Notification notification) {
		notificationRepository.save(notification);
		log.info("{} Notification logged for recipient {}", notification.getContactType(),
				notification.getRecipientId());
	}
}
