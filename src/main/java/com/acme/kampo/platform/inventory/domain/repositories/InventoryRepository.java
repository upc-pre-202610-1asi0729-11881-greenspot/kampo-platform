package com.acme.kampo.platform.inventory.domain.repositories;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Inventory} aggregate.
 *
 * <p>This interface lives in the <strong>domain layer</strong> and defines
 * what persistence operations the domain needs — with no coupling to JPA,
 * Spring Data, or any other infrastructure concern.
 *
 * <p>The infrastructure layer provides the concrete implementation
 * ({@code InventoryRepositoryImpl}) via dependency inversion.
 */
public interface InventoryRepository {
    /**
     * Persists a new or updated Inventory aggregate.
     * After saving, the returned instance has its {@code InventoryId} populated.
     *
     * @param inventory the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Inventory save(Inventory inventory);
    /**
     * Finds an Inventory aggregate by its surrogate key.
     *
     * @param id the raw Long identifier
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Inventory> findById(Long id);
    /**
     * Returns all Inventory aggregates in the system.
     *
     * @return a list of all inventory items, possibly empty
     */
    List<Inventory> findAll();

    Optional<Inventory> findByName(String name);

    List<Inventory> findAllByStatus(InventoryStatus status);

    boolean existsByName(String name);

}
