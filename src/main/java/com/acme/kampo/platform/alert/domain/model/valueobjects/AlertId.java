package com.acme.kampo.platform.alert.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of an Alert aggregate.
 * Immutable — use {@code AlertId.of(...)} to construct instances.
 */
@Embeddable
public record AlertId(Long value) {

    public AlertId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("AlertId value must be positive, got: " + value);
    }

    public static AlertId of(Long value) { return new AlertId(value); }
    public static AlertId of(long value) { return new AlertId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "AlertId(" + value + ")"; }
}
