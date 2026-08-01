/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "bidder-internal-services")
public record InternalServiceProperties(String identityAndAuthService) {
}
