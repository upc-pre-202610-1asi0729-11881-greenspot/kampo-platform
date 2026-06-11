package com.acme.kampo.platform.organization.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Fundo aggregate.
 * Immutable — use {@code FundoId.of(...)} to construct instances.
 */
@Embeddable
public record FundoId(Long value) {

    public FundoId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("FundoId value must be positive, got: " + value);
    }

    public static FundoId of(Long value) { return new FundoId(value); }
    public static FundoId of(long value) { return new FundoId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "FundoId(" + value + ")"; }
}
