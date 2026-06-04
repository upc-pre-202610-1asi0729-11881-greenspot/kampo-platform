package com.acme.kampo.platform.inventory.domain.model.events;

/**
 * Domain event published when a new OrderInput is successfully placed.
 *
 * @param orderId     the ID assigned to the new order
 * @param inventoryId the inventory item being ordered
 * @param supplierId  the supplier fulfilling the order
 * @param quantity    the number of units ordered
 */
public record OrderInputCreatedEvent(
        Long orderId,
        Long inventoryId,
        Long supplierId,
        int quantity
) {}