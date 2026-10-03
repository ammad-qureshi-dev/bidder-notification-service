/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.services;

import java.util.*;

import com.bidder.notification_service.external.config.ExternalServiceProperties;
import com.bidder.notification_service.external.config.IdentityAndAuthServiceEndpoints;
import com.bidder.notification_service.external.dtos.PreferredContactMethod;
import dtos.response.AppUserDto;
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
	private final ExternalServiceProperties props;
	private final IdentityAndAuthServiceEndpoints paths;

	public PreferredContactMethod getPreferredContactType(UUID appUserId) {
		var uri = props.getIdentityAndAuthUri();
		var url = UriComponentsBuilder.fromUriString(uri + paths.getPreferredContact()).toUriString();

		log.info("Calling {}", url);
		var response = restClient.get().uri(url).header("X-App-User-Id", appUserId.toString()).retrieve()
				.body(new ParameterizedTypeReference<ApiResponse<PreferredContactMethod>>() {
				});

		if (response == null) {
			log.error("Response was null for request {}", url);
			throw new RuntimeException("No response received from catalog-service. Item not found. Please check logs");
		}

		return response.getData();
	}

	public Map<ContactType, String> getContactMethods(UUID appUserId) {
		if (appUserId == null) {
			return Map.of(ContactType.APP, "");
		}

		var uri = props.getIdentityAndAuthUri();

		var url = UriComponentsBuilder.fromUriString(uri).path(paths.getContactMethods())
				.queryParam("appUserIds", List.of(appUserId)).build().toUriString();

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

	public Optional<AppUserDto> getAppUser(UUID appUserId) {
		if (appUserId == null) {
			log.error("App-User-Id not provided");
			return Optional.empty();
		}

		var url = UriComponentsBuilder.fromUriString(props.getIdentityAndAuthUri()).path(paths.getAppUser())
				.buildAndExpand(appUserId).toUriString();

		log.info("Calling {}", url);
		var response = restClient.get().uri(url).retrieve()
				.body(new ParameterizedTypeReference<ApiResponse<AppUserDto>>() {
				});

		if (response == null) {
			log.error("Response was null for request {}", url);
			throw new NoSuchElementException(
					"No response received from auth-and-identity-service. AppUser not found. Please check logs");
		}

		return Optional.of(response.getData());
	}
}
