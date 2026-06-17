package com.acme.kampo.platform.inventory.interfaces.events;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;

/**
 * Integration event published by the {@code inventory} bounded context when a new
 * {@link Inventory} item has been successfully created and persisted.
 *
 * <p>This is the <em>published language</em> of the {@code inventory} context.
 * Other bounded contexts must listen to this event rather than to the internal
 * {@link com.acme.kampo.platform.inventory.domain.model.events.InventoryCreatedEvent},
 * which is an internal concern of the {@code inventory} domain.</p>
 *
 * @param inventoryId the identity assigned to the newly created inventory item
 * @param name        the name of the inventory item
 * @param quantity    the initial stock quantity
 * @param unit        the unit of measure
 */
public record InventoryCreatedIntegrationEvent(
        Long inventoryId,
        String name,
        int quantity,
        String unit) {

    /**
     * Convenience factory that extracts all fields from a saved {@link Inventory}.
     *
     * @param inventory the saved inventory (must already carry a non-null id)
     * @return a fully populated {@link InventoryCreatedIntegrationEvent}
     */
    public static InventoryCreatedIntegrationEvent from(Inventory inventory) {
        return new InventoryCreatedIntegrationEvent(
                inventory.getId().getValue(),
                inventory.getName(),
                inventory.getQuantity(),
                inventory.getUnit());
    }
}
