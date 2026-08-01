/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import org.springframework.web.util.UriComponentsBuilder;
import response.ApiResponse;

@Slf4j
@RequiredArgsConstructor
public class ExternalService {

	private final RestClient restClient;

	public <T> T get(String uri, Object... params) {
		var url = UriComponentsBuilder.fromUriString(uri).buildAndExpand(params).toUriString();

		log.info("Calling {}", url);
		var response = restClient.get().uri(url).retrieve().body(new ParameterizedTypeReference<ApiResponse<T>>() {
		});

		if (response == null) {
			log.error("Response was null for request {}", url);
			throw new RuntimeException("No response received from catalog-service. Item not found. Please check logs");
		}

		return response.getData();
	}
}
