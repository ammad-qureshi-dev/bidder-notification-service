/* (C) 2026 
bidder.app */
package models.dtos.response;

import java.util.UUID;

import models.ContactType;
import models.NotificationStatus;

public record SendNotificationResponse(NotificationStatus status, ContactType sentVia, UUID recipientId) {
}
