package com.acme.kampo.platform.alert.application.internal.eventhandlers;

import com.acme.kampo.platform.alert.domain.model.events.AlertRuleCreatedEvent;
import com.acme.kampo.platform.alert.interfaces.events.AlertRuleCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Translates {@link AlertRuleCreatedEvent} into {@link AlertRuleCreatedIntegrationEvent}
 * for cross-context consumers.
 */
@Service("alertAlertRuleCreatedEventHandler")
public class AlertsAlertRuleCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public AlertsAlertRuleCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(AlertRuleCreatedEvent event) {
        eventPublisher.publishEvent(AlertRuleCreatedIntegrationEvent.from(event.alertRule()));
    }
}