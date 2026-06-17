package com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.field.domain.model.enums.FieldVisitStatus;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit} aggregate.
 * Translation handled by {@link com.acme.kampo.platform.field.infrastructure.persistence.jpa.assemblers.FieldVisitPersistenceAssembler}.
 */
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

    public Long            getFieldId()                    { return fieldId; }
    public void setFieldId(Long fieldId)                   { this.fieldId = fieldId; }
    public Long            getAgentId()                    { return agentId; }
    public void setAgentId(Long agentId)                   { this.agentId = agentId; }
    public LocalDateTime   getScheduledAt()                { return scheduledAt; }
    public void setScheduledAt(LocalDateTime scheduledAt)  { this.scheduledAt = scheduledAt; }
    public LocalDateTime   getDoneAt()                     { return doneAt; }
    public void setDoneAt(LocalDateTime doneAt)            { this.doneAt = doneAt; }
    public FieldVisitStatus getStatus()                    { return status; }
    public void setStatus(FieldVisitStatus status)         { this.status = status; }
}