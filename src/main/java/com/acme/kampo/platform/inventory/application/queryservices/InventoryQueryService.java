package com.acme.kampo.platform.inventory.application.queryservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllInventoryQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetInventoryByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Inventory read operations.
 */
public interface InventoryQueryService {

    /**
     * Finds a single Inventory item by ID.
     *
     * @param query the query carrying the inventory ID
     * @return an {@link Optional} with the aggregate, or empty if not found
     */
    Optional<Inventory> handle(GetInventoryByIdQuery query);

    /**
     * Returns all Inventory items.
     *
     * @param query the query (no parameters)
     * @return a list of all inventory items, possibly empty
     */
    List<Inventory> handle(GetAllInventoryQuery query);
}
