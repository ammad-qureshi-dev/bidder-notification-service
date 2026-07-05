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

import static models.NotificationSubject.VERIFY_ACCOUNT;
import static models.NotificationSubject.WELCOME_TO_BIDDER;
import static models.NotificationType.ACTION_REQUIRED;
import static models.NotificationType.INFO;
import static models.TemplateName.ACCOUNT_VERIFICATION;
import static models.TemplateName.WELCOME_REGISTRATION;

@Component
public class NotificationConfig {
	private static final Map<TemplateName, Pair<NotificationType, NotificationSubject>> TEMPLATE_AND_TYPE = Map.of(
			WELCOME_REGISTRATION, Pair.of(INFO, WELCOME_TO_BIDDER), ACCOUNT_VERIFICATION,
			Pair.of(ACTION_REQUIRED, VERIFY_ACCOUNT));

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
