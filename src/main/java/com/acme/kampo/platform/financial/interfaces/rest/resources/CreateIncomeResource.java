package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Inbound resource for registering a new income entry.
 */
@Schema(description = "Resource to register a new income entry")
public record CreateIncomeResource(

        @Schema(description = "Description of the income source", example = "Venta de papa amarilla al mercado mayorista")
        String description,

        @Schema(description = "Monetary amount — must be positive", example = "2000.00")
        BigDecimal amount,

        @Schema(description = "ISO-4217 currency code", example = "PEN")
        String currency,

        @Schema(description = "ID of the fundo this income belongs to", example = "1")
        Long fundoId,

        @Schema(description = "Date the income was received", example = "2025-06-15")
        LocalDate date
) {}