package models.dtos.response;

import models.ContactType;
import models.NotificationStatus;

import java.util.UUID;

public record SendNotificationResponse(
        NotificationStatus status,
        ContactType sentVia,
        UUID recipientId
) {
}
