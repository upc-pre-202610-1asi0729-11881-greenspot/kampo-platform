package com.acme.kampo.platform.alert.application.internal.eventhandlers;

import com.acme.kampo.platform.alert.domain.model.events.AlertSentEvent;
import com.acme.kampo.platform.alert.interfaces.events.AlertSentIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link AlertSentEvent} domain event.
 *
 * <p>Translates the internal domain event into an {@link AlertSentIntegrationEvent}
 * and re-publishes it on the Spring event bus. This is the only place where a domain
 * event crosses the boundary between the domain layer and the published language of the
 * {@code alert} bounded context.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link AlertSentIntegrationEvent},
 * never to the internal {@link AlertSentEvent}.</p>
 */
@Service("alertAlertSentEventHandler")
public class AlertSentEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public AlertSentEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link AlertSentEvent} and publishes the
     * corresponding {@link AlertSentIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event carrying the saved alert
     */
    @EventListener
    public void on(AlertSentEvent event) {
        eventPublisher.publishEvent(AlertSentIntegrationEvent.from(event.alert()));
    }
}