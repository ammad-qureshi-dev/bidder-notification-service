/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import jakarta.validation.Valid;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.SendNotificationResponse;
import org.springframework.stereotype.Service;

@Service
public class MobileService {

	public SendNotificationResponse sendSms(@Valid SendNotificationRequest request) {
		throw new RuntimeException("Not yet implemented");
	}
}
