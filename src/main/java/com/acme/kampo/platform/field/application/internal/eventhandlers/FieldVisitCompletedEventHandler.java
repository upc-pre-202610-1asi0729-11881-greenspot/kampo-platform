package com.acme.kampo.platform.field.application.internal.eventhandlers;

import com.acme.kampo.platform.field.domain.model.events.FieldVisitCompletedEvent;
import com.acme.kampo.platform.field.interfaces.events.FieldVisitCompletedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal handler for {@link FieldVisitCompletedEvent}.
 * Republishes as {@link FieldVisitCompletedIntegrationEvent} for cross-context consumers.
 */
@Service("fieldoperationFieldVisitCompletedEventHandler")
public class FieldVisitCompletedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public FieldVisitCompletedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(FieldVisitCompletedEvent event) {
        eventPublisher.publishEvent(
                FieldVisitCompletedIntegrationEvent.from(event.fieldVisit()));
    }
}