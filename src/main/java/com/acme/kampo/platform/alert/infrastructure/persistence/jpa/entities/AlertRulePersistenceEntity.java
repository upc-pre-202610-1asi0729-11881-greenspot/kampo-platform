package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule} aggregate.
 * Extends {@link AuditableAbstractPersistenceEntity} to inherit {@code id}, {@code createdAt} and {@code updatedAt}.
 * Translation handled by {@link com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers.AlertRulePersistenceAssembler}.
 */
@Setter
@Getter
@Entity
@Table(name = "alert_rules")
public class AlertRulePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(name = "reading_type", nullable = false, length = 20)
    private ReadingType readingType;

    @Enumerated(EnumType.STRING)
    @Column(name = "condition_operator", nullable = false, length = 20)
    private ConditionOperator conditionOperator;

    @Column(nullable = false)
    private Double threshold;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SeverityLevel severity;

    @Column(name = "field_id", nullable = false)
    private Long fieldId;

    public AlertRulePersistenceEntity() {}

}