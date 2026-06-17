package com.acme.kampo.platform.inventory.domain.model.command;

/**
 * Command to place a new input order for an inventory item.
 *
 * @param inventoryId ID of the inventory item being ordered
 * @param supplierId  ID of the supplier fulfilling the order
 * @param quantity    number of units being ordered — must be > 0
 */
public record OrderInputCommand(
        Long inventoryId,
        Long supplierId,
        int quantity
) {
    public OrderInputCommand {
        if (inventoryId == null || inventoryId <= 0)
            throw new IllegalArgumentException("inventoryId must be positive");
        if (supplierId == null || supplierId <= 0)
            throw new IllegalArgumentException("supplierId must be positive");
        if (quantity <= 0)
            throw new IllegalArgumentException("Ordered quantity must be greater than zero");
    }
}