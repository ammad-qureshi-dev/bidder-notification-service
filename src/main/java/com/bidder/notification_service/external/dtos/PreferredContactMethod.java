/* (C) 2026 
bidder.app */
package com.bidder.notification_service.external.dtos;

import java.util.UUID;

import models.ContactType;

public record PreferredContactMethod(ContactType type, String value, UUID appUserId) {
}
