/* (C) 2026 
bidder.app */
package com.bidder.notification_service;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;

import static com.bidder.notification_service.utils.Constants.NOTIFICATION_TOPIC;

@Slf4j
@SpringBootApplication
@EntityScan("models.entities")
public class NotificationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationServiceApplication.class, args);
	}

	@Bean
	NewTopic notification() {
		return TopicBuilder.name(NOTIFICATION_TOPIC).partitions(1).replicas(1).build();
	}
}
