/* (C) 2026 
bidder.app */
package models.dtos.request;

import java.util.Map;
import java.util.UUID;

import jakarta.validation.constraints.NotNull;
import models.ContactType;
import models.TemplateName;

public record SendNotificationRequest(
		// AppUserId
		@NotNull UUID recipientId,

		@NotNull TemplateName template,

		Map<ContactType, String> recipientConfig,

		Map<String, Object> templateData) {
}

/*
 * Sample Payload: { "recipientId": "93f93825-75e5-4fc0-bb5a-3ee309361294",
 * 
 * "template": "WELCOME_REGISTRATION",
 * 
 * "recipientConfig": { "MOBILE": "+1 647-870-1917", "EMAIL":
 * "ammadq3@gmail.com" },
 * 
 * "templateData": { "fullName": "loser" } }
 */
