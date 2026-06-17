package com.acme.kampo.platform.organization.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Field aggregate.
 * Immutable — use {@code FieldId.of(...)} to construct instances.
 */
@Embeddable
public record FieldId(Long value) {

    public FieldId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("FieldId value must be positive, got: " + value);
    }

    public static FieldId of(Long value) { return new FieldId(value); }
    public static FieldId of(long value) { return new FieldId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "FieldId(" + value + ")"; }
}