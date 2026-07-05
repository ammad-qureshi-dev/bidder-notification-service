package com.bidder.notification_service.mappers;

import com.bidder.notification_service.config.NotificationTypeConfig;
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.entities.Notification;

public class NotificationMapper {

    public static Notification requestToEntity(SendNotificationRequest request) {
        return Notification.builder()
                .contactType(request.contactType())
                .type(NotificationTypeConfig.getConfiguredType(request.templateName()))
                .templateName(request.templateName())
                .recipientId(request.recipientId())
                .status(NotificationStatus.SENT)
                .recipientContact(request.recipientContact())
                .build();
    }
}
