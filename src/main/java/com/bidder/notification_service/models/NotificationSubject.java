package com.bidder.notification_service.models;

import lombok.Getter;

@Getter
public enum NotificationSubject {
    VERIFY_ACCOUNT("Verfiy your account"),
    WELCOME_TO_BIDDER("Welcome to Bidder");

    private final String subject;

    NotificationSubject(String subject) {
        this.subject = subject;
    }

}
