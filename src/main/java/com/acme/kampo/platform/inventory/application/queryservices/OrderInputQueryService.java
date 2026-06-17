package com.acme.kampo.platform.inventory.application.queryservices;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllOrderInputsQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetOrderInputByIdQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for OrderInput read operations.
 */
public interface OrderInputQueryService {

    /**
     * Finds a single OrderInput by ID.
     *
     * @param query the query carrying the order ID
     * @return an {@link Optional} with the aggregate, or empty if not found
     */
    Optional<OrderInput> handle(GetOrderInputByIdQuery query);

    /**
     * Returns all OrderInputs.
     *
     * @param query the query (no parameters)
     * @return a list of all orders, possibly empty
     */
    List<OrderInput> handle(GetAllOrderInputsQuery query);
}
