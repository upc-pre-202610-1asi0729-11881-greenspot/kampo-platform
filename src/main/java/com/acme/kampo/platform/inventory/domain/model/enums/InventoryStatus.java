package com.acme.kampo.platform.inventory.domain.model.enums;

/**
 * Represents the stock availability state of an Inventory item.
 *
 * <p>Transitions driven by {@code Inventory#evaluateMinStock()}:
 * <pre>
 *   quantity == 0          → OUT_OF_STOCK
 *   quantity <= minStock   → LOW_STOCK
 *   quantity >  minStock   → AVAILABLE
 * </pre>
 */
public enum InventoryStatus {
    /**
     * Sufficient stock is available — quantity is above the minimum threshold.
     */
    AVAILABLE,
    /**
     * Stock is at or below the configured minimum — a reorder should be triggered.
     */
    LOW_STOCK,
    /**
     * No units remain in stock — the item is completely exhausted.
     */
    OUT_OF_STOCK;
    /**
     * Derives the correct status based on current quantity and the minimum threshold.
     *
     * @param quantity current stock quantity (must be >= 0)
     * @param minStock configured minimum stock level (must be >= 0)
     * @return the matching InventoryStatus
     */
    public static InventoryStatus from(int quantity, int minStock) {
        if(quantity < 0)throw new IllegalArgumentException("Quantity must be negative");
        if(quantity == 0) return OUT_OF_STOCK;
        if(quantity <= minStock) return LOW_STOCK;
        return AVAILABLE;
    }
}
