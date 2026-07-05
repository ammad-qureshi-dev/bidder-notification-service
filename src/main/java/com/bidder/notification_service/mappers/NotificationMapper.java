/* (C) 2026 
bidder.app */
package com.bidder.notification_service.mappers;

import com.bidder.notification_service.config.NotificationConfig;
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.NotificationResponseDto;
import models.entities.Notification;

public class NotificationMapper {

	public static Notification requestToEntity(SendNotificationRequest request) {
		return Notification.builder().contactType(request.contactType())
				.type(NotificationConfig.getConfiguredType(request.templateName())).templateName(request.templateName())
				.recipientId(request.recipientId()).status(NotificationStatus.SENT)
				.subject(NotificationConfig.getConfiguredSubject(request.templateName()))
				.recipientContact(request.recipientContact()).build();
	}

	public static NotificationResponseDto entityToResponse(Notification n) {
		return new NotificationResponseDto(n.getType(), n.getStatus(),
				NotificationConfig.getConfiguredSubject(n.getTemplateName()).getSubject());
	}
}
