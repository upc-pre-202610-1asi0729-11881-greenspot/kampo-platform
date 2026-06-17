package com.acme.kampo.platform.financial.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Inbound resource for updating an existing income entry.
 */
@Schema(description = "Resource to update an existing income entry")
public record UpdateIncomeResource(

        @Schema(description = "Updated description", example = "Venta de papa blanca")
        String description,

        @Schema(description = "Updated monetary amount — must be positive", example = "2500.00")
        BigDecimal amount
) {}
 
