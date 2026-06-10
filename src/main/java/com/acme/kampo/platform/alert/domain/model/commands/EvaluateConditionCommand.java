package com.acme.kampo.platform.alert.domain.model.commands;

/**
 * Command to evaluate the condition of an existing alert rule.
 */
public record EvaluateConditionCommand(Long alertRuleId) {
    public EvaluateConditionCommand {
        if (alertRuleId == null || alertRuleId <= 0)
            throw new IllegalArgumentException("alertRuleId must be positive");
    }
}