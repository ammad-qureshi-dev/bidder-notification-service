package com.bidder.notification_service.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static com.bidder.notification_service.utils.Constants.Controller.BASE_URI;
import static com.bidder.notification_service.utils.Constants.Controller.V1;

@RestController
@RequestMapping(BASE_URI + V1 + "/notification")
public class NotificationController {

    @PostMapping("/send")
    public ResponseEntity<UUID> sendNotification() {
        return null;
    }
}
