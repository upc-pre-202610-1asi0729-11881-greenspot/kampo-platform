package com.acme.kampo.platform.inventory.domain.model.queries;

/**
 * Query to retrieve a single Supplier by its surrogate key.
 *
 * @param supplierId the raw Long identifier of the supplier
 */
public record GetSupplierByIdQuery(Long supplierId) {
    public GetSupplierByIdQuery {
        if (supplierId == null || supplierId <= 0)
            throw new IllegalArgumentException("supplierId must be positive");
    }
}