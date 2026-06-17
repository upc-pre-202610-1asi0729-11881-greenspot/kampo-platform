package com.acme.kampo.platform.alert.domain.model.enums;

/**
 * Represents the urgency level of a triggered {@link com.acme.kampo.platform.alert.domain.model.aggregates.Alert}.
 *
 * <p>Priority is set when the alert is sent and drives how quickly
 * the recipient should act on it.</p>
 */
public enum AlertPriority {

    /** Informational — no immediate action required. */
    LOW,

    /** Attention recommended — should be reviewed soon. */
    MEDIUM,

    /** Immediate action required — critical condition detected. */
    HIGH;

    /** Returns {@code true} if this priority requires immediate attention. */
    public boolean isUrgent() {
        return this == HIGH;
    }
}
