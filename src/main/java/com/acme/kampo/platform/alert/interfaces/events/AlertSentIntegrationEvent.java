package com.acme.kampo.platform.alert.interfaces.events;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;

/**
 * Integration event published when a new Alert is sent.
 *
 * @param alertId     the identity assigned to the newly created alert
 * @param fieldId     the field that triggered the alert
 * @param priority    the alert priority
 */
public record AlertSentIntegrationEvent(
        Long alertId,
        Long fieldId,
        String priority) {

    public static AlertSentIntegrationEvent from(Alert alert) {
        return new AlertSentIntegrationEvent(
                alert.getId().getValue(),
                alert.getFieldId().getValue(),
                alert.getPriority().name());
    }
}