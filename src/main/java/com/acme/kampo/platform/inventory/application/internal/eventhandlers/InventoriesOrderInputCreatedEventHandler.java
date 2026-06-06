package com.acme.kampo.platform.inventory.application.internal.eventhandlers;


import com.acme.kampo.platform.inventory.domain.model.events.OrderInputCreatedEvent;
import com.acme.kampo.platform.inventory.interfaces.events.OrderInputCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link OrderInputCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link OrderInputCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link OrderInputCreatedIntegrationEvent},
 * never to the internal {@link OrderInputCreatedEvent}.</p>
 */
@Service("inventoryOrderInputCreatedEventHandler")
public class InventoriesOrderInputCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public InventoriesOrderInputCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link OrderInputCreatedEvent} and publishes the
     * corresponding {@link OrderInputCreatedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event carrying the saved order
     */
    @EventListener
    public void on(OrderInputCreatedEvent event) {
        eventPublisher.publishEvent(OrderInputCreatedIntegrationEvent.from(event.order()));
    }
}