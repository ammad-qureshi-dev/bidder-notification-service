/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@EnableConfigurationProperties(IdentityAndAuthServiceEndpoints.class)
public class ExternalServiceProperties {

	@Value("${internal-services.identity-and-auth-service}")
	private String identityAndAuthUri;
}
