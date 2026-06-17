package com.acme.kampo.platform.inventory.interfaces.events;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;

/**
 * Integration event published by the {@code inventory} bounded context when a new
 * {@link OrderInput} has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code inventory} context.
 * Other bounded contexts must listen to this event rather than to the internal
 * {@link com.acme.kampo.platform.inventory.domain.model.events.OrderInputCreatedEvent}.</p>
 *
 * @param orderId     the identity assigned to the newly created order
 * @param inventoryId the inventory item being ordered
 * @param supplierId  the supplier fulfilling the order
 * @param quantity    the number of units ordered
 */
public record OrderInputCreatedIntegrationEvent(
        Long orderId,
        Long inventoryId,
        Long supplierId,
        int quantity) {

    /**
     * Convenience factory that extracts all fields from a saved {@link OrderInput}.
     *
     * @param order the saved order (must already carry a non-null id)
     * @return a fully populated {@link OrderInputCreatedIntegrationEvent}
     */
    public static OrderInputCreatedIntegrationEvent from(OrderInput order) {
        return new OrderInputCreatedIntegrationEvent(
                order.getId().getValue(),
                order.getInventoryId().getValue(),
                order.getSupplierId().getValue(),
                order.getQuantity());
    }
}
