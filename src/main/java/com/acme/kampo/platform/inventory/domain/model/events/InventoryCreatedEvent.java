package com.acme.kampo.platform.inventory.domain.model.events;

/**
 * Domain event published when a new Inventory item is successfully created.
 *
 * <p>Carries enough data for consumers to react without an extra query:
 * the new inventory ID, its name, initial quantity and unit.</p>
 *
 * @param inventoryId the ID assigned to the new inventory item
 * @param name        the name of the inventory item
 * @param quantity    the initial stock quantity
 * @param unit        the unit of measure
 */
public record InventoryCreatedEvent(
        Long inventoryId,
        String name,
        int quantity,
        String unit
) {}
