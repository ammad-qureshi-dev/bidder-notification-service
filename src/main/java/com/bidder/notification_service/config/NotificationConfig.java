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

import static models.NotificationSubject.ACCOUNT_VERIFIED;
import static models.NotificationSubject.BID_REQUEST_ACCEPTED;
import static models.NotificationSubject.BID_REQUEST_REJECTED;
import static models.NotificationSubject.BID_REQUEST_SENT;
import static models.NotificationSubject.BID_REQUEST_UPDATED;
import static models.NotificationSubject.PASSWORD_RESET_LINK_SENT;
import static models.NotificationSubject.VERIFY_ACCOUNT;
import static models.NotificationSubject.WELCOME_TO_BIDDER;
import static models.NotificationType.ACTION_REQUIRED;
import static models.NotificationType.INFO;
import static models.NotificationType.SUCCESS;
import static models.NotificationType.WARNING;
import static models.TemplateName.ACCOUNT_VERIFICATION;
import static models.TemplateName.WELCOME_REGISTRATION;

@Component
public class NotificationConfig {
	private static final Map<TemplateName, Pair<NotificationType, NotificationSubject>> TEMPLATE_AND_TYPE = Map.of(
			WELCOME_REGISTRATION, Pair.of(INFO, WELCOME_TO_BIDDER), ACCOUNT_VERIFICATION,
			Pair.of(ACTION_REQUIRED, VERIFY_ACCOUNT), TemplateName.ACCOUNT_VERIFIED,
			Pair.of(SUCCESS, ACCOUNT_VERIFIED), TemplateName.PASSWORD_RESET_LINK_SENT,
			Pair.of(ACTION_REQUIRED, PASSWORD_RESET_LINK_SENT), TemplateName.BID_REQUEST_ACCEPTED,
			Pair.of(SUCCESS, BID_REQUEST_ACCEPTED), TemplateName.BID_REQUEST_REJECTED,
			Pair.of(WARNING, BID_REQUEST_REJECTED), TemplateName.BID_REQUEST_UPDATED,
			Pair.of(INFO, BID_REQUEST_UPDATED), TemplateName.BID_REQUEST_SENT_EMAIL,
			Pair.of(INFO, BID_REQUEST_SENT));

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
