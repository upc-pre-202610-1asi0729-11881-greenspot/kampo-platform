package com.acme.kampo.platform.inventory.application.queryservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllSuppliersQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetSupplierByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Supplier read operations.
 */
public interface SupplierQueryService {

    /**
     * Finds a single Supplier by ID.
     *
     * @param query the query carrying the supplier ID
     * @return an {@link Optional} with the aggregate, or empty if not found
     */
    Optional<Supplier> handle(GetSupplierByIdQuery query);

    /**
     * Returns all Suppliers.
     *
     * @param query the query (no parameters)
     * @return a list of all suppliers, possibly empty
     */
    List<Supplier> handle(GetAllSuppliersQuery query);
}