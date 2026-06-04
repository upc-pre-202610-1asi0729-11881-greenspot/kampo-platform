package com.acme.kampo.platform.inventory.interfaces.rest.resources;
import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;

/**
 * Outbound DTO representing an Inventory item in API responses.
 *
 * @param id       the inventory item ID
 * @param name     name of the item
 * @param quantity current stock quantity
 * @param unit     unit of measure
 * @param minStock minimum stock threshold
 * @param status   current stock status
 */
public record InventoryResource(
        Long id,
        String name,
        int quantity,
        String unit,
        int minStock,
        InventoryStatus status
) {}
