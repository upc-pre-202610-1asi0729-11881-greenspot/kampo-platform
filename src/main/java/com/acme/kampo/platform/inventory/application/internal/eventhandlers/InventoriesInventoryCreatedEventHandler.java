package com.acme.kampo.platform.inventory.application.internal.eventhandlers;

import com.acme.kampo.platform.inventory.domain.model.events.InventoryCreatedEvent;
import com.acme.kampo.platform.inventory.interfaces.events.InventoryCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens for {@link InventoryCreatedEvent} domain events and republishes
 * them as {@link InventoryCreatedIntegrationEvent} for cross-context consumers.
 *
 * <p>This is the anti-corruption boundary between the domain event model
 * and the integration event model — other bounded contexts depend only on
 * the integration event, never on the domain event directly.
 */
@Component
public class InventoriesInventoryCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public InventoriesInventoryCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(InventoryCreatedEvent event) {
        eventPublisher.publishEvent(new InventoryCreatedIntegrationEvent(
                event.inventoryId(),
                event.name(),
                event.quantity(),
                event.unit()
        ));
    }
}
