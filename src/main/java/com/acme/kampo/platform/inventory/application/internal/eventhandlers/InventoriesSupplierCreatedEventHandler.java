package com.acme.kampo.platform.inventory.application.internal.eventhandlers;

import com.acme.kampo.platform.inventory.domain.model.events.SupplierCreatedEvent;
import com.acme.kampo.platform.inventory.interfaces.events.SupplierCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link SupplierCreatedEvent} domain event.
 *
 * <p>Translates the internal domain event into a {@link SupplierCreatedIntegrationEvent}
 * and re-publishes it on the Spring event bus.</p>
 *
 * <p>Other bounded contexts must subscribe to {@link SupplierCreatedIntegrationEvent},
 * never to the internal {@link SupplierCreatedEvent}.</p>
 */
@Service("inventorySupplierCreatedEventHandler")
public class InventoriesSupplierCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public InventoriesSupplierCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Receives the internal {@link SupplierCreatedEvent} and publishes the
     * corresponding {@link SupplierCreatedIntegrationEvent} for cross-context consumers.
     *
     * @param event the internal domain event carrying the saved supplier
     */
    @EventListener
    public void on(SupplierCreatedEvent event) {
        eventPublisher.publishEvent(SupplierCreatedIntegrationEvent.from(event.supplier()));
    }
}
 