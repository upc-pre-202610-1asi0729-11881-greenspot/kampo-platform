package com.acme.kampo.platform.field.application.internal.eventhandlers;

import com.acme.kampo.platform.field.domain.model.events.ObservationRegisteredEvent;
import com.acme.kampo.platform.field.interfaces.events.ObservationRegisteredIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal handler for {@link ObservationRegisteredEvent}.
 * Republishes as {@link ObservationRegisteredIntegrationEvent} for cross-context consumers.
 *
 * <p>The {@code alert} bounded context can subscribe to this integration event
 * to automatically send alerts when severity is HIGH or CRITICAL.</p>
 */
@Service("fieldoperationObservationRegisteredEventHandler")
public class ObservationRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public ObservationRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(ObservationRegisteredEvent event) {
        eventPublisher.publishEvent(
                ObservationRegisteredIntegrationEvent.from(event.observation()));
    }
}