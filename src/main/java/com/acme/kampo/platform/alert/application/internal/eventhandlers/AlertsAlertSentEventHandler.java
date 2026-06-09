package com.acme.kampo.platform.alert.application.internal.eventhandlers;

import com.acme.kampo.platform.alert.domain.model.events.AlertSentEvent;
import com.acme.kampo.platform.alert.interfaces.events.AlertSentIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Translates {@link AlertSentEvent} into {@link AlertSentIntegrationEvent}
 * for cross-context consumers.
 */
@Service("alertAlertSentEventHandler")
public class AlertsAlertSentEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public AlertsAlertSentEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(AlertSentEvent event) {
        eventPublisher.publishEvent(AlertSentIntegrationEvent.from(event.alert()));
    }
}