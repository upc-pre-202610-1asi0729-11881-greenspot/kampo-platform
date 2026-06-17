package com.acme.kampo.platform.financial.domain.model.command;


import java.time.LocalDate;

/**
 * Command to calculate and persist the profitability of a fundo for a given season and period.
 *
 * <p>When handled, the command service fetches the totals of income, expenses and sales
 * for the given period and delegates the net profit and margin calculation to the
 * {@link com.acme.kampo.platform.financial.domain.model.aggregates.Profitability} aggregate
 * constructor.</p>
 *
 * @param fundoId     ID of the fundo to analyse
 * @param seasonId    ID of the agricultural season
 * @param periodStart start of the analysis period (inclusive)
 * @param periodEnd   end of the analysis period (inclusive)
 */
public record CalculateProfitabilityCommand(
        Long fundoId,
        Long seasonId,
        LocalDate periodStart,
        LocalDate periodEnd
) {
    public CalculateProfitabilityCommand {
        if (fundoId == null || fundoId <= 0)
            throw new IllegalArgumentException("fundoId must be positive");
        if (seasonId == null || seasonId <= 0)
            throw new IllegalArgumentException("seasonId must be positive");
        if (periodStart == null)
            throw new IllegalArgumentException("periodStart must not be null");
        if (periodEnd == null)
            throw new IllegalArgumentException("periodEnd must not be null");
        if (!periodEnd.isAfter(periodStart))
            throw new IllegalArgumentException("periodEnd must be after periodStart");
    }
}