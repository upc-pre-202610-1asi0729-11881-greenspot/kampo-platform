package com.acme.kampo.platform.field.domain.model.enums;

/**
 * Represents the severity level of a pest or disease observed during a field visit.
 *
 * <p>Used in {@link com.acme.kampo.platform.fieldoperation.domain.model.aggregates.Observation}
 * to classify both pest severity and disease severity independently.</p>
 */
public enum Severity {

    /** Minor presence — monitor but no immediate intervention required. */
    LOW,

    /** Moderate presence — treatment recommended within the week. */
    MEDIUM,

    /** Significant presence — prompt treatment required to prevent spread. */
    HIGH,

    /** Severe infestation or infection — immediate intervention required. */
    CRITICAL;

    /**
     * Returns {@code true} if this severity requires immediate intervention.
     * Useful to trigger urgent alerts or escalations.
     */
    public boolean isCritical() {
        return this == CRITICAL;
    }

    /**
     * Returns {@code true} if this severity warrants at least some form of intervention
     * (HIGH or CRITICAL).
     */
    public boolean requiresIntervention() {
        return this == HIGH || this == CRITICAL;
    }
}