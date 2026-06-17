package com.acme.kampo.platform.alert.domain.model.commands;

import com.acme.kampo.platform.alert.domain.model.enums.AlertPriority;

/**
 * Command to send (create) a new alert triggered by a rule violation.
 *
 * @param message     human-readable description of what occurred
 * @param priority    urgency level of the alert
 * @param fieldId     the field where the condition was detected
 * @param alertRuleId the rule that triggered this alert
 */
public record SendAlertCommand(
        String message,
        AlertPriority priority,
        Long fieldId,
        Long alertRuleId
) {
    public SendAlertCommand {
        if (message == null || message.isBlank())
            throw new IllegalArgumentException("message must not be blank");
        if (priority == null)
            throw new IllegalArgumentException("priority must not be null");
        if (fieldId == null || fieldId <= 0)
            throw new IllegalArgumentException("fieldId must be positive");
        if (alertRuleId == null || alertRuleId <= 0)
            throw new IllegalArgumentException("alertRuleId must be positive");
    }
}
