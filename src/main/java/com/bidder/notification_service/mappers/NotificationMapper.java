/* (C) 2026 
bidder.app */
package com.bidder.notification_service.mappers;

import com.bidder.notification_service.config.NotificationConfig;
import models.ContactType;
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.NotificationResponseDto;
import models.entities.Notification;

public class NotificationMapper {

	public static Notification requestToEntity(SendNotificationRequest request, ContactType contactType,
			String recipientContact) {
		return Notification.builder().type(NotificationConfig.getConfiguredType(request.template()))
				.template(request.template()).recipientId(request.recipientId()).status(NotificationStatus.SENT)
				.subject(NotificationConfig.getConfiguredSubject(request.template())).contactType(contactType)
				.recipientContact(recipientContact).build();
	}

	public static NotificationResponseDto entityToResponse(Notification n) {
		return new NotificationResponseDto(n.getType(), n.getStatus(),
				NotificationConfig.getConfiguredSubject(n.getTemplate()).getSubject());
	}
}
