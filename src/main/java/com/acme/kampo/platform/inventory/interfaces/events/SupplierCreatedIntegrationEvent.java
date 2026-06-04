package com.acme.kampo.platform.inventory.interfaces.events;


/**
 * Integration event published when a Supplier is registered.
 *
 * @param supplierId the ID of the newly created supplier
 * @param name       the supplier's name
 * @param email      the supplier's email address
 */
public record SupplierCreatedIntegrationEvent(
        Long supplierId,
        String name,
        String email
) {}