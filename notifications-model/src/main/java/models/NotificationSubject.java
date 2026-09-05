/* (C) 2026 
bidder.app */
package models;

import lombok.Getter;

@Getter
public enum NotificationSubject {
	SETUP_CONTACT_METHOD("Please set up a primary contact method"), VERIFY_ACCOUNT(
			"Verify your account"), WELCOME_TO_BIDDER("Welcome to Bidder"), ACCOUNT_VERIFIED(
					"Your account is verified"), PASSWORD_RESET_LINK_SENT("Reset your password"), PASSWORD_UPDATED(
							"Your password was changed"), BID_REQUEST_ACCEPTED(
									"Your bid was accepted"), BID_REQUEST_REJECTED(
											"Update on your bid"), BID_REQUEST_UPDATED(
													"Your bid has been updated"), BID_REQUEST_SENT(
															"Bid placed successfully"), AUCTION_CLOSED(
																	"Your auction has closed"), AUCTION_LIVE(
																			"Your auction is now live"), AUCTION_PAUSED(
																					"Your auction has been paused");

	private final String subject;

	NotificationSubject(String subject) {
		this.subject = subject;
	}

}
