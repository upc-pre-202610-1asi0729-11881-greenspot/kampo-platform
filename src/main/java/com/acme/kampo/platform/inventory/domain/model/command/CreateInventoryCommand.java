package com.acme.kampo.platform.inventory.domain.model.command;

/**
 * Command to register a new inventory item.
 *
 * @param name     human-readable name of the item (e.g. "Fertilizante NPK")
 * @param quantity initial stock quantity — must be >= 0
 * @param unit     unit of measure (e.g. "kg", "L", "units")
 * @param minStock minimum stock threshold that triggers LOW_STOCK status — must be >= 0
 */
public record CreateInventoryCommand(
        String name,
        int quantity,
        String unit,
        int minStock
) {
    public CreateInventoryCommand {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("Inventory name must not be blank");
        if (quantity < 0)
            throw new IllegalArgumentException("Initial quantity cannot be negative");
        if (unit == null || unit.isBlank())
            throw new IllegalArgumentException("Unit must not be blank");
        if (minStock < 0)
            throw new IllegalArgumentException("minStock cannot be negative");
    }
}