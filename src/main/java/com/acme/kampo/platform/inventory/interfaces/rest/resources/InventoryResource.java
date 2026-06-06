package com.acme.kampo.platform.inventory.interfaces.rest.resources;
import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing an Inventory item in API responses.
 */
@Schema(description = "Inventory item representation")
public record InventoryResource(

        @Schema(description = "Unique identifier of the inventory item", example = "1")
        Long id,

        @Schema(description = "Name of the inventory item", example = "Fertilizante NPK")
        String name,

        @Schema(description = "Current stock quantity", example = "100")
        int quantity,

        @Schema(description = "Unit of measure", example = "kg")
        String unit,

        @Schema(description = "Minimum stock threshold", example = "20")
        int minStock,

        @Schema(description = "Current stock status — AVAILABLE, LOW_STOCK or OUT_OF_STOCK",
                example = "AVAILABLE")
        InventoryStatus status
) {}
