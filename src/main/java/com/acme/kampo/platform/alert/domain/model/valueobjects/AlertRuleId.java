package com.acme.kampo.platform.alert.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Typed identity for the AlertRule aggregate.
 */
@Embeddable
public record AlertRuleId(Long value) {

    public AlertRuleId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("AlertRuleId must be positive, got: " + value);
    }

    public static AlertRuleId of(Long value) { return new AlertRuleId(value); }
    public static AlertRuleId of(long value) { return new AlertRuleId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "AlertRuleId(" + value + ")"; }
}