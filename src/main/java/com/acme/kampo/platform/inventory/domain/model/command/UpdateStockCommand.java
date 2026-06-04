package com.acme.kampo.platform.inventory.domain.model.command;

/**
 * Command to adjust the stock quantity of an existing inventory item.
 * A positive delta adds stock; a negative delta removes it.
 *
 * @param inventoryId the ID of the inventory item to update
 * @param delta       quantity to add (positive) or subtract (negative)
 */
public record UpdateStockCommand(
        Long inventoryId,
        int delta
) {
    public UpdateStockCommand {
        if (inventoryId == null || inventoryId <= 0)
            throw new IllegalArgumentException("inventoryId must be a positive value");
        if (delta == 0)
            throw new IllegalArgumentException("delta must be non-zero — use a positive or negative value");
    }
}