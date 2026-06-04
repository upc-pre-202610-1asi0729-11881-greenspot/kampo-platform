package com.acme.kampo.platform.inventory.interfaces.events;

/**
 * Integration event published when an OrderInput is placed.
 *
 * @param orderId     the ID of the newly created order
 * @param inventoryId the inventory item being ordered
 * @param supplierId  the supplier fulfilling the order
 * @param quantity    the number of units ordered
 */
public record OrderInputCreatedIntegrationEvent(
        Long orderId,
        Long inventoryId,
        Long supplierId,
        int quantity
) {}
