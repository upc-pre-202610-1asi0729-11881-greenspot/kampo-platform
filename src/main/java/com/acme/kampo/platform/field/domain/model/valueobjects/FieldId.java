package com.acme.kampo.platform.field.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the identity of a field monitored during a field visit.
 *
 * <p>This is a local copy of the {@code FieldId} concept — each bounded context
 * owns its model independently. The value refers to the same physical field as
 * {@code organization.FieldId} but without any coupling to that context.</p>
 *
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