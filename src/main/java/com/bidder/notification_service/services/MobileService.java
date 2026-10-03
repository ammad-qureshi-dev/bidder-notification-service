/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import jakarta.validation.Valid;
import models.dtos.request.NotifyRequest;
import models.dtos.response.SendNotificationResponse;
import org.springframework.stereotype.Service;

@Service
public class MobileService implements Notifier {

	@Override
	public SendNotificationResponse notify(@Valid NotifyRequest request, String value) {
		throw new RuntimeException("Not yet implemented");
	}
}
