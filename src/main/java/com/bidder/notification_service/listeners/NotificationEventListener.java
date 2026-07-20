/* (C) 2026 
bidder.app */
package com.bidder.notification_service.listeners;

import com.bidder.notification_service.services.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.dtos.request.SendNotificationRequest;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import static com.bidder.notification_service.utils.Constants.NOTIFICATION_TOPIC;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener {

	private final NotificationService notificationService;

	@KafkaListener(topics = NOTIFICATION_TOPIC, groupId = "${spring.kafka.consumer.group-id}")
	public void onNotificationEvent(SendNotificationRequest request) {
		try {
			log.info("consuming request");
			notificationService.send(request);
		} catch (Exception e) {
			log.error("Failed to process notification event for recipient {}", request.recipientId(), e);
		}
	}
}
