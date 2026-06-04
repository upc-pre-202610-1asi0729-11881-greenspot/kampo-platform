package com.acme.kampo.platform.inventory.interfaces.rest.resources;

/**
 * Inbound resource for placing a new input order.
 * Received as JSON body in POST /api/v1/order-inputs.
 */
public record CreateOrderInputResource(
        Long inventoryId,
        Long supplierId,
        int quantity
) {}
