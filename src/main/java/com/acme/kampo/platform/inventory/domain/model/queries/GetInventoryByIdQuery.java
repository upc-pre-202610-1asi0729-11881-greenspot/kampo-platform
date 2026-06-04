package com.acme.kampo.platform.inventory.domain.model.queries;

/**
 * Query to retrieve a single Inventory item by its surrogate key.
 *
 * @param inventoryId the raw Long identifier of the inventory item
 */
public record GetInventoryByIdQuery(Long inventoryId) {
    public GetInventoryByIdQuery {
        if (inventoryId == null || inventoryId <= 0)
            throw new IllegalArgumentException("inventoryId must be positive");
    }
}
