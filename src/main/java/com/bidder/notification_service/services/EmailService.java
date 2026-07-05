package com.bidder.notification_service.services;

import com.bidder.notification_service.config.NotificationTypeConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import models.ContactType;
import models.NotificationStatus;
import models.dtos.request.SendNotificationRequest;
import models.dtos.response.SendNotificationResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailService {

    @Value("${MAIL_USERNAME}")
    private String emailFrom;

    private final Configuration freeMarkerConfig;
    private final JavaMailSender emailSender;

    private static final String DATA_MODEL_NAME = "data";
    private static final int RETRY_LIMIT = 3;

    public SendNotificationResponse sendEmail(SendNotificationRequest request) throws TemplateException, MessagingException, IOException {
        int tries = 0;

        var email = request.recipientContact();
        if (!StringUtils.hasLength(email)) {
            throw new IllegalStateException("Email cannot be sent, no email provided");
        }

        while (true) {
            try {
                tries++;
                emailSender.send(generateHtmlMessage(request));
                log.info("Email sent to {}", request.recipientId());
                break;
            } catch (RuntimeException | IOException | TemplateException | MessagingException e) {
                log.error("Failed to send email on try #{}. Retrying...", tries, e);

                if (tries >= RETRY_LIMIT) {
                    log.error("Failed to send email, retries exceeded", e);
                    throw e;
                }
            }
        }

        return new SendNotificationResponse(NotificationStatus.SENT, ContactType.EMAIL, request.recipientId());
    }

 private MimeMessage generateHtmlMessage(SendNotificationRequest request) throws IOException, TemplateException, MessagingException {
     Template template = freeMarkerConfig.getTemplate(request.templateName().getPath());
     StringWriter writer = new StringWriter();

     template.process(
             Map.of(DATA_MODEL_NAME, request.templateData()), writer
     );

     MimeMessage mimeMessage = emailSender.createMimeMessage();
     MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
     helper.setTo(request.recipientContact());
     helper.setSubject(NotificationTypeConfig.getConfiguredSubject(request.templateName()).getSubject());
     helper.setText(writer.toString(), ContactType.EMAIL.equals(request.contactType()));
     helper.setFrom(emailFrom);

     return mimeMessage;
 }
}
