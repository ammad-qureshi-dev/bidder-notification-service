/* (C) 2026 
bidder.app */
package models;

import lombok.Getter;

@Getter
public enum TemplateName {
	WELCOME_REGISTRATION_EMAIL("email/welcome-registration.ftl"), ACCOUNT_VERIFICATION_EMAIL(
			"email/account-verification.ftl");

	private final String path;

	TemplateName(String path) {
		this.path = path;
	}
}