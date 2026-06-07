package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Inbound resource for registering a new crop sale.
 */
@Schema(description = "Resource to register a new crop sale")
public record CreateSaleResource(

        @Schema(description = "Name of the crop being sold", example = "Papa Amarilla")
        String cropName,

        @Schema(description = "Number of units sold — must be positive", example = "100.0")
        Double quantity,

        @Schema(description = "Price per unit — must be positive", example = "2.50")
        BigDecimal pricePerUnit,

        @Schema(description = "ISO-4217 currency code", example = "PEN")
        String currency,

        @Schema(description = "ID of the fundo this sale belongs to", example = "1")
        Long fundoId,

        @Schema(description = "Date the sale was made", example = "2025-06-20")
        LocalDate date
) {}