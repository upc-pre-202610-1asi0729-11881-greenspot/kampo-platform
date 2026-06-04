package com.acme.kampo.platform.inventory.interfaces.rest.resources;

/**
 * Inbound DTO for creating a new Inventory item.
 * Received as JSON body in POST /api/v1/inventory.
 *
 * @param name     human-readable name of the item
 * @param quantity initial stock quantity
 * @param unit     unit of measure (e.g. "kg", "L", "units")
 * @param minStock minimum stock threshold
 */
public record CreateInventoryResource(
        String name,
        int quantity,
        String unit,
        int minStock
) {}
