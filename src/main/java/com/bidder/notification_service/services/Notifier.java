/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import models.dtos.request.SendNotificationRequest;
import models.dtos.response.SendNotificationResponse;

public interface Notifier {

	SendNotificationResponse send(SendNotificationRequest request);
}
