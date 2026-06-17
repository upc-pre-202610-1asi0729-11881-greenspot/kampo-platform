package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Outbound resource representing a Sale in API responses.
 */
@Schema(description = "Sale representation")
public record SaleResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Name of the crop sold", example = "Papa Amarilla")
        String cropName,

        @Schema(description = "Number of units sold", example = "100.0")
        Double quantity,

        @Schema(description = "Price per unit", example = "2.50")
        BigDecimal pricePerUnit,

        @Schema(description = "Total sale amount (pricePerUnit × quantity)", example = "250.00")
        BigDecimal totalAmount,

        @Schema(description = "ISO-4217 currency code", example = "PEN")
        String currency,

        @Schema(description = "ID of the fundo", example = "1")
        Long fundoId,

        @Schema(description = "Date the sale was made", example = "2025-06-20")
        LocalDate date,

        @Schema(description = "Whether the sale has been cancelled", example = "false")
        boolean cancelled
) {}