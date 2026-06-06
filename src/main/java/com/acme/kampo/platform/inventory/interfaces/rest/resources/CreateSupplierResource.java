package com.acme.kampo.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for registering a new Supplier.
 * Received as JSON body in POST /api/v1/suppliers.
 */
@Schema(description = "Resource to register a new supplier")
public record CreateSupplierResource(

        @Schema(description = "Supplier company or person name", example = "AgroSup S.A.")
        String name,

        @Schema(description = "Phone number or contact reference", example = "+51 999 888 777")
        String contact,

        @Schema(description = "Supplier email address", example = "contacto@agrosup.pe")
        String email
) {}
