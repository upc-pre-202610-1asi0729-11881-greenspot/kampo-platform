package com.acme.kampo.platform.inventory.domain.repositories;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link OrderInput} aggregate.
 *
 * <p>This interface lives in the <strong>domain layer</strong> and defines
 * what persistence operations the domain needs — with no coupling to JPA,
 * Spring Data, or any other infrastructure concern.
 *
 * <p>The infrastructure layer provides the concrete implementation
 * ({@code OrderInputRepositoryImpl}) via dependency inversion.
 */
public interface OrderInputRepository {

    /**
     * Persists a new or updated OrderInput aggregate.
     * After saving, the returned instance has its {@code OrderId} populated.
     *
     * @param orderInput the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    OrderInput save(OrderInput orderInput);

    /**
     * Finds an OrderInput aggregate by its surrogate key.
     *
     * @param id the raw Long identifier
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<OrderInput> findById(Long id);

    /**
     * Returns all OrderInput aggregates in the system.
     *
     * @return a list of all orders, possibly empty
     */
    List<OrderInput> findAll();

    List<OrderInput> findAllByInventoryId(Long inventoryId);

    List<OrderInput> findAllByStatus(OrderStatus status);

    boolean hasPendingOrders(Long inventoryId);
}