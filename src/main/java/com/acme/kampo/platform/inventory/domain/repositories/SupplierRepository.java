package com.acme.kampo.platform.inventory.domain.repositories;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Supplier} aggregate.
 *
 * <p>This interface lives in the <strong>domain layer</strong> and defines
 * what persistence operations the domain needs — with no coupling to JPA,
 * Spring Data, or any other infrastructure concern.
 *
 * <p>The infrastructure layer provides the concrete implementation
 * ({@code SupplierRepositoryImpl}) via dependency inversion.
 */
public interface SupplierRepository {

    /**
     * Persists a new or updated Supplier aggregate.
     * After saving, the returned instance has its {@code SupplierId} populated.
     *
     * @param supplier the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Supplier save(Supplier supplier);

    /**
     * Finds a Supplier aggregate by its surrogate key.
     *
     * @param id the raw Long identifier
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Supplier> findById(Long id);

    /**
     * Returns all Supplier aggregates in the system.
     *
     * @return a list of all suppliers, possibly empty
     */
    List<Supplier> findAll();
}
