package com.acme.kampo.platform.alert.domain.model.enums;

/**
 * Represents the severity of an {@link com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule}.
 *
 * <p>Severity describes how serious the condition is if the rule triggers,
 * while {@link AlertPriority} describes how urgently the resulting alert
 * should be handled. They are related but independent concepts.</p>
 */
public enum SeverityLevel {

    /** Minor condition — monitor but no immediate risk. */
    LOW,

    /** Moderate condition — may affect crop quality if unaddressed. */
    MEDIUM,

    /** Critical condition — immediate intervention required to prevent damage. */
    HIGH;

    /** Returns {@code true} if this severity represents a critical condition. */
    public boolean isCritical() {
        return this == HIGH;
    }
}
