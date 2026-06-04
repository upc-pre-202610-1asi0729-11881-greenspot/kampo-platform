package com.acme.kampo.platform.inventory.application.internal.eventhandlers;


import com.acme.kampo.platform.inventory.domain.model.events.OrderInputCreatedEvent;
import com.acme.kampo.platform.inventory.interfaces.events.OrderInputCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens for {@link OrderInputCreatedEvent} domain events and republishes
 * them as {@link OrderInputCreatedIntegrationEvent} for cross-context consumers.
 */
@Component
public class InventoriesOrderInputCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public InventoriesOrderInputCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(OrderInputCreatedEvent event) {
        eventPublisher.publishEvent(new OrderInputCreatedIntegrationEvent(
                event.orderId(),
                event.inventoryId(),
                event.supplierId(),
                event.quantity()
        ));
    }
}

