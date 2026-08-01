/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.services;

import java.util.Map;
import java.util.UUID;

import com.bidder.notification_service.external.config.AuthAndIdentityServiceProperties;
import com.bidder.notification_service.external.config.InternalServiceProperties;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ContactType;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import response.ApiResponse;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthAndIdentityService {

	private final RestClient restClient;
	private InternalServiceProperties serviceUrls;
	private AuthAndIdentityServiceProperties props;

	public Pair<ContactType, String> getPreferredContactType(UUID appUserId) {
		var url = UriComponentsBuilder.fromUriString(serviceUrls.identityAndAuthService() + props.getPreferredContact())
				.toUriString();

		log.info("Calling {}", url);
		var response = restClient.get().uri(url).header("X-App-User-Id", appUserId.toString()).retrieve()
				.body(new ParameterizedTypeReference<ApiResponse<Pair<ContactType, String>>>() {
				});

		if (response == null) {
			log.error("Response was null for request {}", url);
			throw new RuntimeException("No response received from catalog-service. Item not found. Please check logs");
		}

		return response.getData();
	}

	public Map<ContactType, String> getContactMethods(UUID appUserId) {
		var url = UriComponentsBuilder.fromUriString(serviceUrls.identityAndAuthService() + props.getContactsMethods())
				.toUriString();

		log.info("Calling {}", url);
		var response = restClient.get().uri(url).header("X-App-User-Id", appUserId.toString()).retrieve()
				.body(new ParameterizedTypeReference<ApiResponse<Map<ContactType, String>>>() {
				});

		if (response == null) {
			log.error("Response was null for request {}", url);
			throw new RuntimeException("No response received from catalog-service. Item not found. Please check logs");
		}

		return response.getData();
	}
}
