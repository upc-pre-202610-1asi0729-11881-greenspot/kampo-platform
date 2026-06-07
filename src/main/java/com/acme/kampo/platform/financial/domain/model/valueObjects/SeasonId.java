package com.acme.kampo.platform.financial.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Season in the financial context.
 * Immutable — use {@code SeasonId.of(...)} to construct instances.
 */
@Embeddable
public record SeasonId(Long value) {

    public SeasonId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("SeasonId value must be positive, got: " + value);
    }

    public static SeasonId of(Long value) { return new SeasonId(value); }
    public static SeasonId of(long value) { return new SeasonId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "SeasonId(" + value + ")"; }
}