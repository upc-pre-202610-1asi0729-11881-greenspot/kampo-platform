package com.acme.kampo.platform.financial.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Income in the financial context.
 * Immutable — use {@code IncomeId.of(...)} to construct instances.
 */
@Embeddable
public record IncomeId(Long value) {

    public IncomeId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("IncomeId value must be positive, got: " + value);
    }

    public static IncomeId of(Long value) { return new IncomeId(value); }
    public static IncomeId of(long value) { return new IncomeId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "IncomeId(" + value + ")"; }
}
