package com.acme.kampo.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for creating a new Inventory item.
 * Received as JSON body in POST /api/v1/inventory.
 */
@Schema(description = "Resource to create a new inventory item")
public record CreateInventoryResource(

        @Schema(description = "Human-readable name of the inventory item", example = "Fertilizante NPK")
        String name,

        @Schema(description = "Initial stock quantity — must be zero or positive", example = "100")
        int quantity,

        @Schema(description = "Unit of measure", example = "kg")
        String unit,

        @Schema(description = "Minimum stock threshold — triggers LOW_STOCK when quantity reaches this value", example = "20")
        int minStock
) {}
