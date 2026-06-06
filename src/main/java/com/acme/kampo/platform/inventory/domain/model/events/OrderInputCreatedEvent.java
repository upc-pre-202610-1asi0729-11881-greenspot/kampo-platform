package com.acme.kampo.platform.inventory.domain.model.events;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;

/**
 * Domain event published when a new {@link OrderInput} is successfully placed.
 *
 * @param order the newly persisted order aggregate
 */
public record OrderInputCreatedEvent(OrderInput order) {}