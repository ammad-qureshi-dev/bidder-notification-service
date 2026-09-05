/* (C) 2026 
bidder.app */
package com.bidder.notification_service.config;

import java.util.Map;
import java.util.NoSuchElementException;

import models.NotificationSubject;
import models.NotificationType;
import models.TemplateName;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import static models.NotificationSubject.*;
import static models.NotificationType.ACTION_REQUIRED;
import static models.NotificationType.INFO;
import static models.NotificationType.SUCCESS;
import static models.NotificationType.WARNING;
import static models.TemplateName.ACCOUNT_VERIFICATION;
import static models.TemplateName.WELCOME_REGISTRATION;

@Component
public class NotificationConfig {
	private static final Map<TemplateName, Pair<NotificationType, NotificationSubject>> TEMPLATE_AND_TYPE = Map
			.ofEntries(Map.entry(WELCOME_REGISTRATION, Pair.of(INFO, WELCOME_TO_BIDDER)),
					Map.entry(ACCOUNT_VERIFICATION, Pair.of(ACTION_REQUIRED, VERIFY_ACCOUNT)),
					Map.entry(TemplateName.ACCOUNT_VERIFIED, Pair.of(SUCCESS, ACCOUNT_VERIFIED)),
					Map.entry(TemplateName.PASSWORD_RESET_LINK_SENT,
							Pair.of(ACTION_REQUIRED, PASSWORD_RESET_LINK_SENT)),
					Map.entry(TemplateName.PASSWORD_UPDATED, Pair.of(SUCCESS, PASSWORD_UPDATED)),
					Map.entry(TemplateName.BID_REQUEST_ACCEPTED, Pair.of(SUCCESS, BID_REQUEST_ACCEPTED)),
					Map.entry(TemplateName.BID_REQUEST_REJECTED, Pair.of(WARNING, BID_REQUEST_REJECTED)),
					Map.entry(TemplateName.BID_REQUEST_UPDATED, Pair.of(INFO, BID_REQUEST_UPDATED)),
					Map.entry(TemplateName.BID_REQUEST_SENT, Pair.of(INFO, BID_REQUEST_SENT)),
					Map.entry(TemplateName.AUCTION_CLOSED, Pair.of(INFO, AUCTION_CLOSED)),
					Map.entry(TemplateName.AUCTION_LIVE, Pair.of(SUCCESS, AUCTION_LIVE)),
					Map.entry(TemplateName.AUCTION_PAUSED, Pair.of(WARNING, AUCTION_PAUSED)),
					Map.entry(TemplateName.CONTACT_METHOD_SETUP, Pair.of(ACTION_REQUIRED, SETUP_CONTACT_METHOD)));

	public static NotificationType getConfiguredType(TemplateName templateName) {
		return getTemplateConfiguration(templateName).getFirst();
	}

	public static NotificationSubject getConfiguredSubject(TemplateName templateName) {
		return getTemplateConfiguration(templateName).getSecond();
	}

	public static Pair<NotificationType, NotificationSubject> getTemplateConfiguration(TemplateName templateName) {
		if (!TEMPLATE_AND_TYPE.containsKey(templateName)) {
			throw new NoSuchElementException("Template Name not found");
		}

		return TEMPLATE_AND_TYPE.get(templateName);
	}
}
