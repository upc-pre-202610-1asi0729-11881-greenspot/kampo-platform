package com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.field.domain.model.enums.FieldVisitStatus;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit} aggregate.
 * Translation handled by {@link com.acme.kampo.platform.field.infrastructure.persistence.jpa.assemblers.FieldVisitPersistenceAssembler}.
 */
@Setter
@Getter
@Entity
@Table(name = "field_visits")
public class FieldVisitPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "field_id", nullable = false)
    private Long fieldId;

    @Column(name = "agent_id", nullable = false)
    private Long agentId;

    @Column(name = "scheduled_at", nullable = false)
    private LocalDateTime scheduledAt;

    @Column(name = "done_at")
    private LocalDateTime doneAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private FieldVisitStatus status;

    public FieldVisitPersistenceEntity() {}

}