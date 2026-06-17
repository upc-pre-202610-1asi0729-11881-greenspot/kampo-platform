package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Inbound resource to trigger a profitability calculation.
 */
@Schema(description = "Resource to calculate profitability for a fundo and season")
public record CalculateProfitabilityResource(

        @Schema(description = "ID of the fundo to analyse", example = "1")
        Long fundoId,

        @Schema(description = "ID of the agricultural season", example = "1")
        Long seasonId,

        @Schema(description = "Start of the analysis period (inclusive)", example = "2025-01-01")
        LocalDate periodStart,

        @Schema(description = "End of the analysis period (inclusive)", example = "2025-06-30")
        LocalDate periodEnd
) {}
 