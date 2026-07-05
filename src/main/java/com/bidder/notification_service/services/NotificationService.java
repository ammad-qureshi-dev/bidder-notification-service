package com.bidder.notification_service.services;

import com.bidder.notification_service.mappers.NotificationMapper;
import com.bidder.notification_service.repositories.NotificationRepository;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ContactType;
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.SendNotificationResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.naming.directory.NoSuchAttributeException;
import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final EmailService emailService;
    private final MobileService mobileService;

    private final NotificationRepository notificationRepository;

    public SendNotificationResponse send(@Valid SendNotificationRequest request) throws NoSuchAttributeException, TemplateException, MessagingException, IOException {
        var contactType = request.contactType();

        switch (contactType) {
            case EMAIL -> {return emailService.sendEmail(request);}
            case PHONE -> {return mobileService.sendSms(request);}
            case APP -> {return sendNotification(request);}
            default -> throw new NoSuchAttributeException("Contact Type does not exist");
        }
    }

    public SendNotificationResponse sendNotification(@Valid SendNotificationRequest request) {
        var notification = NotificationMapper.requestToEntity(request);
        notificationRepository.save(notification);
        log.info("App Notification sent to {}", request.recipientId());
        return new SendNotificationResponse(NotificationStatus.SENT, ContactType.APP, request.recipientId());
    }
}
