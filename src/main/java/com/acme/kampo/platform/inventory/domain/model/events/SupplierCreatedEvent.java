package com.acme.kampo.platform.inventory.domain.model.events;

/**
 * Domain event published when a new Supplier is successfully registered.
 *
 * @param supplierId the ID assigned to the new supplier
 * @param name       the supplier's name
 * @param email      the supplier's email address
 */
public record SupplierCreatedEvent(
        Long supplierId,
        String name,
        String email
) {}