package com.acme.kampo.platform.alert.interfaces.events;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;

/**
 * Integration event published when a new AlertRule is created.
 *
 * @param alertRuleId the identity assigned to the newly created alert rule
 * @param fieldId     the field this rule monitors
 * @param readingType the type of reading being monitored
 */
public record AlertRuleCreatedIntegrationEvent(
        Long alertRuleId,
        Long fieldId,
        String readingType) {

    public static AlertRuleCreatedIntegrationEvent from(AlertRule alertRule) {
        return new AlertRuleCreatedIntegrationEvent(
                alertRule.getId().getValue(),
                alertRule.getFieldId().getValue(),
                alertRule.getReadingType().name());
    }
}