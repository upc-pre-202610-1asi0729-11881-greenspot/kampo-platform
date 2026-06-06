package com.acme.kampo.platform.inventory.domain.model.events;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;

/**
 * Domain event published when a new {@link Inventory} item is successfully created.
 * Carries the full aggregate so consumers can extract whatever fields they need
 * without requiring an extra query.
 *
 * @param inventory the newly persisted inventory aggregate
 */
public record InventoryCreatedEvent(Inventory inventory) {}