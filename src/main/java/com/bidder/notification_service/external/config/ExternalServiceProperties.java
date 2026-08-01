/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ExternalServiceProperties {

	@Bean
	@ConfigurationProperties(prefix = "bidder-internal-services")
	public Map<String, String> bidderInternalServices() {
		return new HashMap<>();
	}
}
