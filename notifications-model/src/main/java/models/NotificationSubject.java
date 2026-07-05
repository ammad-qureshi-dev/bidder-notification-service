/* (C) 2026 
bidder.app */
package models;

import lombok.Getter;

@Getter
public enum NotificationSubject {
	VERIFY_ACCOUNT("Verify your account"), WELCOME_TO_BIDDER("Welcome to Bidder");

	private final String subject;

	NotificationSubject(String subject) {
		this.subject = subject;
	}

}
