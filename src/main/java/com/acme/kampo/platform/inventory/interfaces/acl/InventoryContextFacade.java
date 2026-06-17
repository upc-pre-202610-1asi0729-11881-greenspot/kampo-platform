package com.acme.kampo.platform.inventory.interfaces.acl;

import java.util.Optional;

/**
 * Anti-Corruption Layer facade exposing the Inventory bounded context
 * to other bounded contexts.
 *
 * <p>Other contexts depend on this interface, never on internal application
 * services, aggregates, or repositories. This isolates them from internal
 * implementation changes.
 *
 * <p>All methods work with primitive types or simple value objects —
 * never with domain aggregates from this context.
 */
public interface InventoryContextFacade {

    /**
     * Creates a new inventory item and returns its assigned ID.
     *
     * @param name     item name
     * @param quantity initial quantity
     * @param unit     unit of measure
     * @param minStock minimum stock threshold
     * @return the ID of the newly created inventory item
     */
    Long createInventory(String name, int quantity, String unit, int minStock);

    /**
     * Registers a new supplier and returns its assigned ID.
     *
     * @param name    supplier name
     * @param contact contact reference
     * @param email   supplier email
     * @return the ID of the newly created supplier
     */
    Long createSupplier(String name, String contact, String email);

    /**
     * Places a new input order and returns its assigned ID.
     *
     * @param inventoryId ID of the inventory item being ordered
     * @param supplierId  ID of the supplier fulfilling the order
     * @param quantity    number of units to order
     * @return the ID of the newly created order
     */
    Long createOrderInput(Long inventoryId, Long supplierId, int quantity);

    /**
     * Finds an inventory item ID by its name.
     * Useful for cross-context lookups when only the name is known.
     *
     * @param name the inventory item name
     * @return an {@link Optional} with the ID, or empty if not found
     */
    Optional<Long> fetchInventoryIdByName(String name);
}
