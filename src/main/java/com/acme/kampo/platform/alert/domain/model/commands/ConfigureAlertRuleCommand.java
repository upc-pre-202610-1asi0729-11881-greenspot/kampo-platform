package com.acme.kampo.platform.alert.domain.model.commands;

import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;

/**
 * Command to configure a new alert rule for a field.
 *
 * @param readingType       the type of sensor reading to monitor
 * @param conditionOperator the comparison operator to apply against the threshold
 * @param threshold         the value that triggers the alert when the condition is met
 * @param severity          how critical the condition is if triggered
 * @param fieldId           the field this rule monitors
 */
public record ConfigureAlertRuleCommand(
        ReadingType readingType,
        ConditionOperator conditionOperator,
        Double threshold,
        SeverityLevel severity,
        Long fieldId
) {
    public ConfigureAlertRuleCommand {
        if (readingType == null)
            throw new IllegalArgumentException("readingType must not be null");
        if (conditionOperator == null)
            throw new IllegalArgumentException("conditionOperator must not be null");
        if (threshold == null)
            throw new IllegalArgumentException("threshold must not be null");
        if (severity == null)
            throw new IllegalArgumentException("severity must not be null");
        if (fieldId == null || fieldId <= 0)
            throw new IllegalArgumentException("fieldId must be positive");
    }
}