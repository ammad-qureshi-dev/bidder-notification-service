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
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.NotificationResponseDto;
import models.dtos.response.PageResponse;
import models.dtos.response.SendNotificationResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import response.ApiResponse;

@RestController
@RequestMapping("/api/v1/notification")
@RequiredArgsConstructor
public class NotificationController {

	private final NotificationService notificationService;

	@PostMapping("/send")
	public ResponseEntity<ApiResponse<List<SendNotificationResponse>>> sendNotification(
			@RequestBody SendNotificationRequest request) {
		try {
			var response = notificationService.send(request);
			return ResponseEntity.ok()
					.body(ApiResponse.<List<SendNotificationResponse>>builder().data(response).build());
		} catch (RuntimeException | NoSuchAttributeException | TemplateException | MessagingException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	@GetMapping("/inbox")
	public ResponseEntity<ApiResponse<PageResponse<NotificationResponseDto>>> getNotifications(
			@RequestParam(value = "contactType", required = false) ContactType contactType,
			@RequestHeader("X-App-User-Id") UUID appUserId,
			@PageableDefault(size = 20, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		return ResponseEntity.ok().body(ApiResponse.<PageResponse<NotificationResponseDto>>builder()
				.data(notificationService.getNotifications(contactType, appUserId, pageable)).build());
	}

	@PutMapping("/{notificationId}")
	public ResponseEntity<ApiResponse<UUID>> updateNotificationStatus(
			@RequestParam(value = "status", required = true) NotificationStatus status,
			@PathVariable UUID notificationId) {
		notificationService.updateNotificationStatus(status, notificationId);
		return ResponseEntity.ok().body(ApiResponse.<UUID>builder().data(notificationId).build());
	}
}
