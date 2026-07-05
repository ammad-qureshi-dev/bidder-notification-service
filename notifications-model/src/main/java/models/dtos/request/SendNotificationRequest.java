/* (C) 2026 
bidder.app */
package models.dtos.request;

import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import models.ContactType;
import models.TemplateName;

public record SendNotificationRequest(
		// AppUserId
		@NotNull UUID recipientId,

		@NotNull ContactType contactType,

		// Email / Phone
		@NotNull String recipientContact,

		// This helps identify which template to send
		@NotNull TemplateName templateName,

		// Template data (if needed)
		@NotNull Object templateData) {
}
