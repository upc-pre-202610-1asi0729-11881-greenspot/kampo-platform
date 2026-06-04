package com.acme.kampo.platform.inventory.interfaces.rest.resources;

/**
 * Inbound resource for registering a new Supplier.
 * Received as JSON body in POST /api/v1/suppliers.
 */
public record CreateSupplierResource(
        String name,
        String contact,
        String email
) {}
