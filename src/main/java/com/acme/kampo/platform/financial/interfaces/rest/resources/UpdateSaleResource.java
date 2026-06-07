package com.acme.kampo.platform.financial.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Inbound resource for updating an existing sale.
 */
@Schema(description = "Resource to update an existing sale")
public record UpdateSaleResource(

        @Schema(description = "Updated crop name", example = "Papa Blanca")
        String cropName,

        @Schema(description = "Updated quantity — must be positive", example = "200.0")
        Double quantity,

        @Schema(description = "Updated price per unit — must be positive", example = "3.00")
        BigDecimal pricePerUnit
) {}
