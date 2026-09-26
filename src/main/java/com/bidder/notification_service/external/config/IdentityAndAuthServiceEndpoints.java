/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "identity-and-auth-service")
public record IdentityAndAuthServiceEndpoints(String getPreferredContact, String getContactMethods) {
}
