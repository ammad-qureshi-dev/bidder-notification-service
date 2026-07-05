/* (C) 2026 
bidder.app */
package com.bidder.notification_service.repositories;

import java.util.List;
import java.util.UUID;

import models.ContactType;
import models.entities.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {

	@Query("""
			select n
			from Notification n
			where (:contactType is null or n.contactType = :contactType)
			and n.recipientId = :recipientId
			""")
	List<Notification> findByContactTypeAndRecipientId(@Param("contactType") ContactType contactType,
			@Param("recipientId") UUID recipientId);
}
