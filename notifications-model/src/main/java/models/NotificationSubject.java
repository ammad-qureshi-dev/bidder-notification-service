/* (C) 2026 
bidder.app */
package models;

import lombok.Getter;

@Getter
public enum NotificationSubject {
	VERIFY_ACCOUNT("Verify your account"), WELCOME_TO_BIDDER("Welcome to Bidder"), ACCOUNT_VERIFIED(
			"Your account is verified"), PASSWORD_RESET_LINK_SENT("Reset your password"), BID_REQUEST_ACCEPTED(
					"Your bid was accepted"), BID_REQUEST_REJECTED("Update on your bid"), BID_REQUEST_UPDATED(
							"Your bid has been updated"), BID_REQUEST_SENT("Bid placed successfully");

	private final String subject;

	NotificationSubject(String subject) {
		this.subject = subject;
	}

}
