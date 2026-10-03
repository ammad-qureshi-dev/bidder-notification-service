/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import models.dtos.request.NotifyRequest;
import models.dtos.response.SendNotificationResponse;

public interface Notifier {

	SendNotificationResponse notify(NotifyRequest request, String value);
}
