package com.acme.kampo.platform.financial.domain.model.valueObjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Expense in the financial context.
 * Immutable — use {@code ExpenseId.of(...)} to construct instances.
 */
@Embeddable
public record ExpenseId(Long value) {

    public ExpenseId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("ExpenseId value must be positive, got: " + value);
    }

    public static ExpenseId of(Long value) { return new ExpenseId(value); }
    public static ExpenseId of(long value) { return new ExpenseId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "ExpenseId(" + value + ")"; }
}