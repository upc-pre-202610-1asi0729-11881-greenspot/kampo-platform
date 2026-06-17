package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Outbound resource representing a Profitability result in API responses.
 */
@Schema(description = "Profitability calculation result")
public record ProfitabilityResource(

        @Schema(description = "Unique identifier of this calculation", example = "1")
        Long id,

        @Schema(description = "ID of the fundo analysed", example = "1")
        Long fundoId,

        @Schema(description = "ID of the agricultural season", example = "1")
        Long seasonId,

        @Schema(description = "Total income for the period", example = "3000.00")
        BigDecimal totalIncome,

        @Schema(description = "Total expenses for the period", example = "1000.00")
        BigDecimal totalExpenses,

        @Schema(description = "Total sales for the period", example = "2000.00")
        BigDecimal totalSales,

        @Schema(description = "Net profit (income + sales - expenses)", example = "4000.00")
        BigDecimal netProfit,

        @Schema(description = "ISO-4217 currency code", example = "PEN")
        String currency,

        @Schema(description = "Profit margin as a percentage", example = "80.0")
        Double marginPercentage,

        @Schema(description = "Date and time this calculation was performed", example = "2025-06-30T10:00:00")
        LocalDateTime calculatedAt
) {}
