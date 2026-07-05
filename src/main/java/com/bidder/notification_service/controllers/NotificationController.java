package com.bidder.notification_service.controllers;

import com.bidder.notification_service.services.NotificationService;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.SendNotificationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.NoSuchAttributeException;
import java.io.IOException;

import static com.bidder.notification_service.utils.Constants.Controller.BASE_URI;
import static com.bidder.notification_service.utils.Constants.Controller.V1;

@RestController
@RequestMapping(BASE_URI + V1 + "/notification")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping("/send")
    public ResponseEntity<SendNotificationResponse> sendNotification(
            @RequestBody SendNotificationRequest request
    ) {
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
}
