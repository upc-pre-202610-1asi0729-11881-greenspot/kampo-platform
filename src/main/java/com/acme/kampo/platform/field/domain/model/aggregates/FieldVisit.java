package com.acme.kampo.platform.field.domain.model.aggregates;

import com.acme.kampo.platform.field.domain.model.commands.CompleteFieldVisitCommand;
import com.acme.kampo.platform.field.domain.model.commands.ScheduleFieldVisitCommand;
import com.acme.kampo.platform.field.domain.model.enums.FieldVisitStatus;
import com.acme.kampo.platform.field.domain.model.events.FieldVisitCompletedEvent;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Aggregate root representing a scheduled or completed field visit.
 *
 * <p>Lifecycle:
 * <pre>
 *   SCHEDULED → DONE  (via {@link #complete(CompleteFieldVisitCommand)})
 * </pre>
 *
 * <p>Publishes {@link FieldVisitCompletedEvent} when completed.
 * No event is published on scheduling — that is a pure configuration action.</p>
 */
@Getter
public class FieldVisit extends AbstractDomainAggregateRoot<FieldVisit> {

    private FieldVisitId      id;
    private FieldId           fieldId;
    private Long              agentId;
    private LocalDateTime     scheduledAt;
    private LocalDateTime     doneAt;
    private FieldVisitStatus  status;

    /** Required by JPA proxy — do not use directly. */
    protected FieldVisit() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public FieldVisit(Long id, Long fieldId, Long agentId,
                      LocalDateTime scheduledAt, LocalDateTime doneAt,
                      FieldVisitStatus status) {
        this.id          = FieldVisitId.of(id);
        this.fieldId     = FieldId.of(fieldId);
        this.agentId     = agentId;
        this.scheduledAt = scheduledAt;
        this.doneAt      = doneAt;
        this.status      = status;
    }

    /**
     * Creation constructor — builds from a {@link ScheduleFieldVisitCommand}.
     * Status starts as SCHEDULED. No domain event is published.
     *
     * @param command the scheduling command
     */
    public FieldVisit(ScheduleFieldVisitCommand command) {
        this.fieldId     = FieldId.of(command.fieldId());
        this.agentId     = command.agentId();
        this.scheduledAt = command.scheduledAt();
        this.doneAt      = null;
        this.status      = FieldVisitStatus.SCHEDULED;
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Marks this field visit as completed and registers a {@link FieldVisitCompletedEvent}.
     *
     * @param command the completion command
     * @throws IllegalStateException if the visit is already completed
     */
    public void complete(CompleteFieldVisitCommand command) {
        if (status.isCompleted())
            throw new IllegalStateException(
                    "FieldVisit %d is already completed".formatted(
                            id != null ? id.getValue() : 0));
        this.status = FieldVisitStatus.DONE;
        this.doneAt = LocalDateTime.now();
        registerDomainEvent(new FieldVisitCompletedEvent(this));
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public FieldVisit reconstitute(Long rawId) {
        this.id = FieldVisitId.of(rawId);
        return this;
    }
}