package com.acme.kampo.platform.financial.interfaces.rest.transform;


import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.command.CalculateProfitabilityCommand;
import com.acme.kampo.platform.financial.interfaces.rest.resources.CalculateProfitabilityResource;
import com.acme.kampo.platform.financial.interfaces.rest.resources.ProfitabilityResource;

/**
 * Assembler for Profitability resource ↔ command/domain translations.
 */
public final class ProfitabilityResourceAssembler {

    private ProfitabilityResourceAssembler() {}

    public static CalculateProfitabilityCommand toCommand(CalculateProfitabilityResource resource) {
        return new CalculateProfitabilityCommand(
                resource.fundoId(), resource.seasonId(),
                resource.periodStart(), resource.periodEnd());
    }

    public static ProfitabilityResource toResource(Profitability profitability) {
        return new ProfitabilityResource(
                profitability.getId().getValue(),
                profitability.getFundoId().getValue(),
                profitability.getSeasonId().getValue(),
                profitability.getTotalIncome().amount(),
                profitability.getTotalExpenses().amount(),
                profitability.getTotalSales().amount(),
                profitability.getNetProfit().amount(),
                profitability.getNetProfit().currency(),
                profitability.getMargin(),
                profitability.getCalculatedAt());
    }
}
