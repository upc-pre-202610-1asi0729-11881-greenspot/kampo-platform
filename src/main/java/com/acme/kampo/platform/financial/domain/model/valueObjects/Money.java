package com.acme.kampo.platform.financial.domain.model.valueObjects;


import jakarta.persistence.Embeddable;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Value object representing a monetary amount with its currency.
 *
 * <p>Immutable by design. All arithmetic operations return a new {@link Money} instance.
 * Currency mismatch is always an error — never silently converts.</p>
 *
 * <p>Two decimal places of precision are enforced on construction via
 * {@link RoundingMode#HALF_UP}.</p>
 *
 * @param amount   the monetary amount, always rounded to 2 decimal places
 * @param currency ISO-4217 currency code (e.g. "PEN", "USD")
 */
@Embeddable
public record Money(BigDecimal amount, String currency) {

    /** Scale used for all monetary calculations. */
    private static final int SCALE = 2;
    private static final RoundingMode ROUNDING = RoundingMode.HALF_UP;

    public Money {
        Objects.requireNonNull(amount,   "amount must not be null");
        Objects.requireNonNull(currency, "currency must not be null");
        if (currency.isBlank() || currency.length() != 3)
            throw new IllegalArgumentException(
                    "currency must be a 3-letter ISO-4217 code, got: '" + currency + "'");
        amount = amount.setScale(SCALE, ROUNDING);
    }

    // ── Factory methods ───────────────────────────────────────────────────────

    /**
     * Primary factory method.
     *
     * @param amount   raw monetary value
     * @param currency ISO-4217 currency code
     * @return a new {@link Money} instance
     */
    public static Money of(BigDecimal amount, String currency) {
        return new Money(amount, currency);
    }

    /**
     * Convenience factory for use in tests and seeders.
     *
     * @param amount   plain double value — avoid for production financial calculations
     * @param currency ISO-4217 currency code
     */
    public static Money of(double amount, String currency) {
        return new Money(BigDecimal.valueOf(amount), currency);
    }

    /**
     * Returns a zero-amount {@link Money} in the given currency.
     * Useful as an accumulator identity element.
     */
    public static Money zero(String currency) {
        return new Money(BigDecimal.ZERO, currency);
    }

    // ── Arithmetic ────────────────────────────────────────────────────────────

    /**
     * Adds {@code other} to this amount.
     *
     * @param other the amount to add — must share the same currency
     * @return a new {@link Money} with the sum
     * @throws IllegalArgumentException if currencies differ
     */
    public Money add(Money other) {
        requireSameCurrency(other);
        return new Money(this.amount.add(other.amount), this.currency);
    }

    /**
     * Subtracts {@code other} from this amount.
     * The result may be negative (e.g. a loss position).
     *
     * @param other the amount to subtract — must share the same currency
     * @return a new {@link Money} with the difference
     * @throws IllegalArgumentException if currencies differ
     */
    public Money subtract(Money other) {
        requireSameCurrency(other);
        return new Money(this.amount.subtract(other.amount), this.currency);
    }

    /**
     * Multiplies this amount by a plain numeric factor.
     * Useful for quantity × unit-price calculations.
     *
     * @param factor the multiplier (e.g. quantity sold)
     * @return a new {@link Money} with the product
     */
    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor).setScale(SCALE, ROUNDING), this.currency);
    }

    // ── Predicates ────────────────────────────────────────────────────────────

    /** Returns {@code true} if the amount is strictly greater than zero. */
    public boolean isPositive() {
        return amount.compareTo(BigDecimal.ZERO) > 0;
    }

    /** Returns {@code true} if the amount is exactly zero. */
    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    /** Returns {@code true} if the amount is strictly less than zero (loss/deficit). */
    public boolean isNegative() {
        return amount.compareTo(BigDecimal.ZERO) < 0;
    }

    /**
     * Returns {@code true} if this amount is greater than {@code other}.
     * Currencies must match.
     */
    public boolean isGreaterThan(Money other) {
        requireSameCurrency(other);
        return this.amount.compareTo(other.amount) > 0;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────

    private void requireSameCurrency(Money other) {
        if (!this.currency.equals(other.currency))
            throw new IllegalArgumentException(
                    "Currency mismatch: cannot operate on %s and %s"
                            .formatted(this.currency, other.currency));
    }

    @Override
    public String toString() {
        return "%s %s".formatted(currency, amount.toPlainString());
    }
}
