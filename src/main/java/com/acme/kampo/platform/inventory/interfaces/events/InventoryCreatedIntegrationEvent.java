package com.acme.kampo.platform.inventory.interfaces.events;

/**
 * Integration event published when an Inventory item is created.
 * Consumed by other bounded contexts via the application event bus.
 *
 * @param inventoryId the ID of the newly created inventory item
 * @param name        the name of the inventory item
 * @param quantity    the initial stock quantity
 * @param unit        the unit of measure
 */
public record InventoryCreatedIntegrationEvent(
        Long inventoryId,
        String name,
        int quantity,
        String unit
) {}

