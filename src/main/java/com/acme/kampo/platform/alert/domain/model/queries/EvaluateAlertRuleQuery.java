package com.acme.kampo.platform.alert.domain.model.queries;

import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;

/**
 * Query to evaluate whether a sensor reading violates an alert rule's condition.
 *
 * <p>This was originally modelled as a command ({@code EvaluateConditionCommand})
 * but evaluation is a pure read operation — it does not mutate any aggregate state.
 * Moving it to a query correctly reflects that intent.</p>
 *
 * @param alertRuleId  the rule to evaluate against
 * @param currentValue the current sensor reading to check
 */
public record EvaluateAlertRuleQuery(AlertRuleId alertRuleId, Double currentValue) {
    public EvaluateAlertRuleQuery {
        if (alertRuleId == null)
            throw new IllegalArgumentException("alertRuleId must not be null");
        if (currentValue == null)
            throw new IllegalArgumentException("currentValue must not be null");
    }
}