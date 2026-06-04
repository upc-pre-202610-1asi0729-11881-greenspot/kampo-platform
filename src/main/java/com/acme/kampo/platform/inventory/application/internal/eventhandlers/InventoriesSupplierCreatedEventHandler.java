package com.acme.kampo.platform.inventory.application.internal.eventhandlers;

import com.acme.kampo.platform.inventory.domain.model.events.SupplierCreatedEvent;
import com.acme.kampo.platform.inventory.interfaces.events.SupplierCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Listens for {@link SupplierCreatedEvent} domain events and republishes
 * them as {@link SupplierCreatedIntegrationEvent} for cross-context consumers.
 */
@Component
public class InventoriesSupplierCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public InventoriesSupplierCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SupplierCreatedEvent event) {
        eventPublisher.publishEvent(new SupplierCreatedIntegrationEvent(
                event.supplierId(),
                event.name(),
                event.email()
        ));
    }
}