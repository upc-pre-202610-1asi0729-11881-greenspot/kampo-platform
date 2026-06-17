package com.acme.kampo.platform.field.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of an Observation aggregate.
 * Immutable — use {@code ObservationId.of(...)} to construct instances.
 */
@Embeddable
public record ObservationId(Long value) {

    public ObservationId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("ObservationId value must be positive, got: " + value);
    }

    public static ObservationId of(Long value) { return new ObservationId(value); }
    public static ObservationId of(long value) { return new ObservationId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "ObservationId(" + value + ")"; }
}