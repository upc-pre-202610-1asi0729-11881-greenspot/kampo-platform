package com.acme.kampo.platform.financial.interfaces.events;

import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;

/**
 * Integration event published when a {@link Profitability} result is calculated.
 */
public record ProfitabilityCalculatedIntegrationEvent(
        Long profitabilityId,
        Long fundoId,
        Long seasonId,
        java.math.BigDecimal netProfit,
        String currency,
        Double marginPercentage) {

    public static ProfitabilityCalculatedIntegrationEvent from(Profitability profitability) {
        return new ProfitabilityCalculatedIntegrationEvent(
                profitability.getId().getValue(),
                profitability.getFundoId().getValue(),
                profitability.getSeasonId().getValue(),
                profitability.getNetProfit().amount(),
                profitability.getNetProfit().currency(),
                profitability.getMargin());
    }
}