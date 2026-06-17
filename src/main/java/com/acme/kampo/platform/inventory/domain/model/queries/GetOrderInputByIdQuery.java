package com.acme.kampo.platform.inventory.domain.model.queries;

/**
 * Query to retrieve a single OrderInput by its surrogate key.
 *
 * @param orderId the raw Long identifier of the order
 */
public record GetOrderInputByIdQuery(Long orderId) {
    public GetOrderInputByIdQuery {
        if (orderId == null || orderId <= 0)
            throw new IllegalArgumentException("orderId must be positive");
    }
}
