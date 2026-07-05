/* (C) 2026 
bidder.app */
package com.bidder.notification_service.services;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

import com.bidder.notification_service.config.NotificationConfig;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailService implements Notifier {

	@Value("${MAIL_USERNAME}")
	private String emailFrom;

	private final Configuration freeMarkerConfig;
	private final JavaMailSender emailSender;
	private final AppNotificationService appNotificationService;

	private static final String EMAIL_TEMPLATE_PREFIX = "email/";
	private static final String DATA_MODEL_NAME = "data";
	private static final int RETRY_LIMIT = 3;

	@Override
	public SendNotificationResponse send(SendNotificationRequest request) {
		int tries = 0;

		var email = request.recipientConfig().get(ContactType.EMAIL);
		if (!StringUtils.hasLength(email)) {
			throw new IllegalStateException("Email cannot be sent, no email provided");
		}

		while (true) {
			try {
				tries++;
				emailSender.send(generateHtmlMessage(request));

				// ToDo: bug -- if logging fails, handle differently
				appNotificationService.logNotification(request, ContactType.EMAIL, email);
				log.info("Email sent to {}", request.recipientId());
				break;
			} catch (RuntimeException | IOException | TemplateException | MessagingException e) {
				log.error("Failed to send email on try #{}. Retrying...", tries, e);

				if (tries >= RETRY_LIMIT) {
					log.error("Failed to send email, retries exceeded", e);
					throw new RuntimeException();
				}
			}
		}

		return new SendNotificationResponse(NotificationStatus.SENT, ContactType.EMAIL, request.recipientId());
	}

	private MimeMessage generateHtmlMessage(SendNotificationRequest request)
			throws IOException, TemplateException, MessagingException {
		Template template = freeMarkerConfig.getTemplate(EMAIL_TEMPLATE_PREFIX + request.template().getPath());
		log.debug("Template {} found", request.template());
		StringWriter writer = new StringWriter();

		template.process(Map.of(DATA_MODEL_NAME, request.templateData()), writer);
		log.debug("Data set for {} template", request.template());

		MimeMessage mimeMessage = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "UTF-8");
		helper.setTo(request.recipientConfig().get(ContactType.EMAIL));
		helper.setSubject(NotificationConfig.getConfiguredSubject(request.template()).getSubject());
		helper.setText(writer.toString(), request.recipientConfig().containsKey(ContactType.EMAIL));
		helper.setFrom(emailFrom);

		return mimeMessage;
	}
}
