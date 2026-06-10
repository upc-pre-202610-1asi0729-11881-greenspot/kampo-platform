package com.acme.kampo.platform.alert.domain.model.enums;

/**
 * Represents the comparison operator used by an {@link com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule}
 * to evaluate whether a sensor reading violates the configured threshold.
 *
 * <p>Evaluation: {@code currentValue <operator> threshold}
 * <pre>
 *   GREATER_THAN : currentValue >  threshold → condition met
 *   LESS_THAN    : currentValue <  threshold → condition met
 *   EQUAL        : currentValue == threshold → condition met
 * </pre>
 */
public enum ConditionOperator {

    /** Condition is met when the reading exceeds the threshold. */
    GREATER_THAN,

    /** Condition is met when the reading falls below the threshold. */
    LESS_THAN,

    /** Condition is met when the reading exactly matches the threshold. */
    EQUAL;

    /**
     * Evaluates whether {@code currentValue} satisfies this operator against {@code threshold}.
     *
     * @param currentValue the sensor reading to evaluate
     * @param threshold    the configured alert threshold
     * @return {@code true} if the condition is met
     */
    public boolean evaluate(double currentValue, double threshold) {
        return switch (this) {
            case GREATER_THAN -> currentValue > threshold;
            case LESS_THAN    -> currentValue < threshold;
            case EQUAL        -> Double.compare(currentValue, threshold) == 0;
        };
    }
}