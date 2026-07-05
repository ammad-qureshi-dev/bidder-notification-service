/* (C) 2026 
bidder.app */
package com.bidder.notification_service.controllers;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.naming.directory.NoSuchAttributeException;

import com.bidder.notification_service.services.NotificationService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import models.ContactType;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.NotificationResponseDto;
import models.dtos.response.SendNotificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.bidder.notification_service.utils.Constants.Controller.BASE_URI;
import static com.bidder.notification_service.utils.Constants.Controller.V1;

@RestController
@RequestMapping(BASE_URI + V1 + "/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping("/send")
	public ResponseEntity<SendNotificationResponse> sendNotification(@RequestBody SendNotificationRequest request) {
		try {
			var response = notificationService.send(request);
			return ResponseEntity.ok().body(response);
		} catch (NoSuchAttributeException e) {
			throw new RuntimeException(e);
		} catch (TemplateException e) {
			throw new RuntimeException(e);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/recipient/{recipientId}")
	public ResponseEntity<List<NotificationResponseDto>> getNotifications(
			@RequestParam(value = "contactType", required = false) ContactType contactType,
			@PathVariable UUID recipientId) {
		return ResponseEntity.ok().body(notificationService.getNotifications(contactType, recipientId));
	}
}
