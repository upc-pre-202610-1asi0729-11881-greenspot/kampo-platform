package com.acme.kampo.platform.field.domain.model.aggregates;

import com.acme.kampo.platform.field.domain.model.commands.RegisterObservationCommand;
import com.acme.kampo.platform.field.domain.model.enums.Severity;
import com.acme.kampo.platform.field.domain.model.events.ObservationRegisteredEvent;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.domain.model.valueobjects.ObservationId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root representing an observation recorded during a field visit.
 *
 * <p>An observation captures pest and disease findings, general notes and
 * a treatment recommendation made by the visiting agronomist.</p>
 *
 * <p>Pest and disease fields are optional — a visit may reveal only one type
 * of issue, or the agent may record only general notes.</p>
 *
 * <p>Publishes {@link ObservationRegisteredEvent} on creation.</p>
 */
@Getter
public class Observation extends AbstractDomainAggregateRoot<Observation> {

    private ObservationId id;
    private FieldVisitId  fieldVisitId;
    private String        notes;
    private String        pestName;
    private Severity      pestSeverity;
    private String        diseaseName;
    private Severity      diseaseSeverity;
    private String        recommendation;
    private String        evidenceUrl;

    /** Required by JPA proxy — do not use directly. */
    protected Observation() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public Observation(Long id, Long fieldVisitId, String notes,
                       String pestName, Severity pestSeverity,
                       String diseaseName, Severity diseaseSeverity,
                       String recommendation, String evidenceUrl) {
        this.id              = ObservationId.of(id);
        this.fieldVisitId    = FieldVisitId.of(fieldVisitId);
        this.notes           = notes;
        this.pestName        = pestName;
        this.pestSeverity    = pestSeverity;
        this.diseaseName     = diseaseName;
        this.diseaseSeverity = diseaseSeverity;
        this.recommendation  = recommendation;
        this.evidenceUrl     = evidenceUrl;
    }

    /**
     * Creation constructor — builds from a {@link RegisterObservationCommand}
     * and registers an {@link ObservationRegisteredEvent}.
     *
     * @param command the registration command
     */
    public Observation(RegisterObservationCommand command) {
        this.fieldVisitId    = FieldVisitId.of(command.fieldVisitId());
        this.notes           = command.notes();
        this.pestName        = command.pestName();
        this.pestSeverity    = command.pestSeverity();
        this.diseaseName     = command.diseaseName();
        this.diseaseSeverity = command.diseaseSeverity();
        this.recommendation  = command.recommendation();
        this.evidenceUrl     = command.evidenceUrl();
        registerDomainEvent(new ObservationRegisteredEvent(this));
    }

    // ── Domain query helpers ──────────────────────────────────────────────────

    /**
     * Returns {@code true} if any pest or disease with critical severity was observed.
     * Useful to trigger urgent alerts in the alert bounded context.
     */
    public boolean hasCriticalFindings() {
        return (pestSeverity != null && pestSeverity.isCritical())
                || (diseaseSeverity != null && diseaseSeverity.isCritical());
    }

    /**
     * Returns {@code true} if any pest or disease requiring intervention was observed.
     */
    public boolean requiresIntervention() {
        return (pestSeverity != null && pestSeverity.requiresIntervention())
                || (diseaseSeverity != null && diseaseSeverity.requiresIntervention());
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Observation reconstitute(Long rawId) {
        this.id = ObservationId.of(rawId);
        return this;
    }
}