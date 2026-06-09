package com.acme.kampo.platform.alert.domain.model.commands;

import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;

/**
 * Command to configure a new alert rule.
 */
public record ConfigureAlertRuleCommand(
        ReadingType readingType,
        ConditionOperator conditionOperator,
        SeverityLevel severity,
        Long fieldId
) {
    public ConfigureAlertRuleCommand {
        if (readingType == null)
            throw new IllegalArgumentException("readingType must not be null");
        if (conditionOperator == null)
            throw new IllegalArgumentException("conditionOperator must not be null");
        if (severity == null)
            throw new IllegalArgumentException("severity must not be null");
        if (fieldId == null || fieldId <= 0)
            throw new IllegalArgumentException("fieldId must be positive");
    }
}