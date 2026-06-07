package com.acme.kampo.platform.financial.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Sale in the financial context.
 * Immutable — use {@code SaleId.of(...)} to construct instances.
 */
@Embeddable
public record SaleId(Long value) {

    public SaleId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("SaleId value must be positive, got: " + value);
    }

    public static SaleId of(Long value) { return new SaleId(value); }
    public static SaleId of(long value) { return new SaleId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "SaleId(" + value + ")"; }
}
