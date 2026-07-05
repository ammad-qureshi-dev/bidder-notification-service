package com.bidder.notification_service.config;

import com.bidder.notification_service.models.NotificationSubject;
import models.NotificationType;
import models.TemplateName;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.NoSuchElementException;

import static com.bidder.notification_service.models.NotificationSubject.VERIFY_ACCOUNT;
import static com.bidder.notification_service.models.NotificationSubject.WELCOME_TO_BIDDER;
import static models.NotificationType.ACTION_REQUIRED;
import static models.NotificationType.INFO;
import static models.TemplateName.ACCOUNT_VERIFICATION_EMAIL;
import static models.TemplateName.WELCOME_REGISTRATION_EMAIL;

@Component
public class NotificationTypeConfig {
    private static final Map<TemplateName, Pair<NotificationType, NotificationSubject>> TEMPLATE_AND_TYPE = Map.of(
            WELCOME_REGISTRATION_EMAIL, Pair.of(INFO, WELCOME_TO_BIDDER),
            ACCOUNT_VERIFICATION_EMAIL, Pair.of(ACTION_REQUIRED, VERIFY_ACCOUNT)
    );

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
