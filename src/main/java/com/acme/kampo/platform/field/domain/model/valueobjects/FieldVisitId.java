package com.acme.kampo.platform.field.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a FieldVisit aggregate.
 * Immutable — use {@code FieldVisitId.of(...)} to construct instances.
 */
@Embeddable
public record FieldVisitId(Long value) {

    public FieldVisitId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("FieldVisitId value must be positive, got: " + value);
    }

    public static FieldVisitId of(Long value) { return new FieldVisitId(value); }
    public static FieldVisitId of(long value) { return new FieldVisitId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "FieldVisitId(" + value + ")"; }
}