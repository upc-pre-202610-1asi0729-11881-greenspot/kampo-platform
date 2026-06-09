package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.alert.domain.model.enums.AlertPriority;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the Alert aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "alerts")
public class AlertPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private AlertPriority priority;

    @Column(name = "is_read", nullable = false)
    private boolean isRead;

    @Column(name = "field_id", nullable = false)
    private Long fieldId;

    @Column(name = "alert_rule_id", nullable = false)
    private Long alertRuleId;

    public AlertPersistenceEntity() {}
}