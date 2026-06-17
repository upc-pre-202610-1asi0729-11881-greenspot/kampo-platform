package com.acme.kampo.platform.field.domain.model.enums;

/**
 * Represents the lifecycle status of a {@link com.acme.kampo.platform.fieldoperation.domain.model.aggregates.FieldVisit}.
 *
 * <p>Transitions:
 * <pre>
 *   SCHEDULED → DONE  (via CompleteFieldVisitCommand)
 * </pre>
 * A completed visit is terminal — it cannot be rescheduled.
 */
public enum FieldVisitStatus {

    /** The visit has been scheduled but not yet carried out. */
    SCHEDULED,

    /** The visit has been completed and observations may have been recorded. */
    DONE;

    /**
     * Returns {@code true} if this status represents a completed visit.
     * Useful to guard against completing an already-done visit.
     */
    public boolean isCompleted() {
        return this == DONE;
    }
}