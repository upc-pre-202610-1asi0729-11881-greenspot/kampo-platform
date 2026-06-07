package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Outbound resource representing an Income entry in API responses.
 */
@Schema(description = "Income entry representation")
public record IncomeResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Description of the income source", example = "Venta de papa amarilla")
        String description,

        @Schema(description = "Monetary amount", example = "2000.00")
        BigDecimal amount,

        @Schema(description = "ISO-4217 currency code", example = "PEN")
        String currency,

        @Schema(description = "ID of the fundo", example = "1")
        Long fundoId,

        @Schema(description = "Date the income was received", example = "2025-06-15")
        LocalDate date
) {}
