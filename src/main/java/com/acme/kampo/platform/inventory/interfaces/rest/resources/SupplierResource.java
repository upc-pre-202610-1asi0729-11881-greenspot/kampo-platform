package com.acme.kampo.platform.inventory.interfaces.rest.resources;

/**
 * Outbound resource representing a Supplier in API responses.
 */
public record SupplierResource(
        Long id,
        String name,
        String contact,
        String email
) {}
