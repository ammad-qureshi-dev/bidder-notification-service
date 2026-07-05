/* (C) 2026 
bidder.app */
package models.dtos.response;

import models.NotificationStatus;
import models.NotificationType;
public record NotificationResponseDto(NotificationType type, NotificationStatus status, String subject

// ToDo: render html content somehow
) {
}
