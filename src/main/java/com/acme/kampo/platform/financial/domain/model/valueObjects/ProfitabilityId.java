package com.acme.kampo.platform.financial.domain.model.valueObjects;


import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Profitability in the financial context.
 * Immutable — use {@code ProfitabilityId.of(...)} to construct instances.
 */
@Embeddable
public record ProfitabilityId(Long value) {

    public ProfitabilityId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("ProfitabilityId value must be positive, got: " + value);
    }

    public static ProfitabilityId of(Long value) { return new ProfitabilityId(value); }
    public static ProfitabilityId of(long value) { return new ProfitabilityId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "ProfitabilityId(" + value + ")"; }
}
