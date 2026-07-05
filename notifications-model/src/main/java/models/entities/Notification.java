/* (C) 2026 
bidder.app */
package models.entities;

import java.util.Map;
import java.util.UUID;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import models.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(schema = Constants.Database.SCHEMA, name = "notification")
@EqualsAndHashCode(callSuper = true)
public class Notification extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;

	@NotNull
	// Email / Phone-number
	private String recipientContact;

	@NotNull private UUID recipientId;

	@NotNull @Enumerated(EnumType.STRING)
	private NotificationSubject subject;

	@NotNull @Enumerated(EnumType.STRING)
	private TemplateName templateName;

	@JdbcTypeCode(SqlTypes.JSON)
	@Column(columnDefinition = "jsonb")
	private Map<String, Object> templateData;

	@Enumerated(EnumType.STRING)
	private ContactType contactType;

	@Enumerated(EnumType.STRING)
	private NotificationType type;

	@Enumerated(EnumType.STRING)
	private NotificationStatus status;
}
