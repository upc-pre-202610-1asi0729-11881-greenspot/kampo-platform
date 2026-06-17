package com.acme.kampo.platform.alert.interfaces.events;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.enums.AlertPriority;

/**
 * Integration event published by the {@code alert} bounded context when a new
 * {@link Alert} is successfully sent.
 *
 * <p>This is the <em>published language</em> of the {@code alert} context.
 * Other bounded contexts must subscribe to this event, never to the internal
 * {@link com.acme.kampo.platform.alert.domain.model.events.AlertSentEvent}.</p>
 *
 * @param alertId     the ID of the newly created alert
 * @param message     the alert message
 * @param priority    the alert priority
 * @param fieldId     the field where the condition was detected
 * @param alertRuleId the rule that triggered the alert
 */
public record AlertSentIntegrationEvent(
        Long alertId,
        String message,
        AlertPriority priority,
        Long fieldId,
        Long alertRuleId) {

    /**
     * Convenience factory that extracts all fields from a saved {@link Alert}.
     *
     * @param alert the saved alert (must already carry a non-null id)
     * @return a fully populated {@link AlertSentIntegrationEvent}
     */
    public static AlertSentIntegrationEvent from(Alert alert) {
        return new AlertSentIntegrationEvent(
                alert.getId().getValue(),
                alert.getMessage(),
                alert.getPriority(),
                alert.getFieldId().getValue(),
                alert.getAlertRuleId().getValue());
    }
}