package com.acme.kampo.platform.inventory.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing a Supplier in API responses.
 */
@Schema(description = "Supplier representation")
public record SupplierResource(

        @Schema(description = "Unique identifier of the supplier", example = "1")
        Long id,

        @Schema(description = "Supplier company or person name", example = "AgroSup S.A.")
        String name,

        @Schema(description = "Phone number or contact reference", example = "+51 999 888 777")
        String contact,

        @Schema(description = "Supplier email address", example = "contacto@agrosup.pe")
        String email
) {}
