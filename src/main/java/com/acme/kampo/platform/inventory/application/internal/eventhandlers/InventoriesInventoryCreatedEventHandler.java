package com.acme.kampo.platform.inventory.application.internal.eventhandlers;

import com.acme.kampo.platform.inventory.domain.model.events.InventoryCreatedEvent;
import com.acme.kampo.platform.inventory.interfaces.events.InventoryCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link InventoryCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link InventoryCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus. This is the only place where a domain
 * event crosses the boundary between the domain layer and the published language of the
 * {@code inventory} bounded context.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link InventoryCreatedIntegrationEvent}
 * (from {@code inventory.interfaces.events}), never to the internal
 * {@link InventoryCreatedEvent}.</p>
 */
@Service("inventoryInventoryCreatedEventHandler")
public class InventoriesInventoryCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public InventoriesInventoryCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link InventoryCreatedEvent} and publishes the
     * corresponding {@link InventoryCreatedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event carrying the saved inventory
     */
    @EventListener
    public void on(InventoryCreatedEvent event) {
        eventPublisher.publishEvent(InventoryCreatedIntegrationEvent.from(event.inventory()));
    }
}