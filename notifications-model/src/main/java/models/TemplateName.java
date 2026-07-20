/* (C) 2026 
bidder.app */
package models;

import lombok.Getter;

@Getter
public enum TemplateName {

	PASSWORD_RESET_LINK_SENT("/password_reset_link_sent.ftl"), ACCOUNT_VERIFIED(
			"/account_verified.ftl"), BID_REQUEST_ACCEPTED("/bid_request_accepted.ftl"), BID_REQUEST_REJECTED(
					"/bid_request_rejected.ftl"), BID_REQUEST_UPDATED(
							"/bid_request_updated.ftl"), BID_REQUEST_SENT(
									"/bid_request_sent.ftl"), WELCOME_REGISTRATION(
											"/welcome-registration.ftl"), ACCOUNT_VERIFICATION(
													"/account-verification.ftl");

	private final String path;

	TemplateName(String path) {
		this.path = path;
	}
}