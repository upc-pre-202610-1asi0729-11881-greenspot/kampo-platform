package com.acme.kampo.platform.inventory.domain.model.events;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;

/**
 * Domain event published when a new {@link Supplier} is successfully registered.
 *
 * @param supplier the newly persisted supplier aggregate
 */
public record SupplierCreatedEvent(Supplier supplier) {}
