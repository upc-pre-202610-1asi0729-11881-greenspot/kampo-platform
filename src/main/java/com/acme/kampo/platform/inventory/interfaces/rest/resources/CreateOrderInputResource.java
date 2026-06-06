package com.acme.kampo.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for placing a new input order.
 * Received as JSON body in POST /api/v1/order-inputs.
 */
@Schema(description = "Resource to place a new input order")
public record CreateOrderInputResource(

        @Schema(description = "ID of the inventory item being ordered", example = "1")
        Long inventoryId,

        @Schema(description = "ID of the supplier fulfilling the order", example = "1")
        Long supplierId,

        @Schema(description = "Number of units to order — must be greater than zero", example = "50")
        int quantity
) {}