package com.acme.kampo.platform.financial.interfaces.events;

import com.acme.kampo.platform.financial.domain.model.aggregates.Income;

/**
 * Integration event published when a new {@link Income} entry is registered.
 */
public record IncomeRegisteredIntegrationEvent(
        Long incomeId,
        Long fundoId,
        java.math.BigDecimal amount,
        String currency) {

    public static IncomeRegisteredIntegrationEvent from(Income income) {
        return new IncomeRegisteredIntegrationEvent(
                income.getId().getValue(),
                income.getFundoId().getValue(),
                income.getAmount().amount(),
                income.getAmount().currency());
    }
}
