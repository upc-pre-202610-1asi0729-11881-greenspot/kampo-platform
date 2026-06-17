package com.acme.kampo.platform.financial.interfaces.acl;


import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;

import java.util.Optional;

/**
 * Anti-Corruption Layer facade exposing the Financial bounded context
 * to other bounded contexts.
 *
 * <p>All methods work with primitives or {@link Money} — never with internal
 * domain aggregates. This shields consumers from internal implementation changes.</p>
 */
public interface FinancialContextFacade {

    /**
     * Returns the sum of all expenses for a given fundo.
     *
     * @param fundoId the fundo identifier
     * @return total expenses as {@link Money}
     */
    Money fetchExpenseTotalByFundo(Long fundoId);

    /**
     * Returns the sum of all income entries for a given fundo.
     *
     * @param fundoId the fundo identifier
     * @return total income as {@link Money}
     */
    Money fetchIncomeTotalByFundo(Long fundoId);

    /**
     * Returns the sum of all non-cancelled sales for a given fundo.
     *
     * @param fundoId the fundo identifier
     * @return total sales as {@link Money}
     */
    Money fetchSaleTotalByFundo(Long fundoId);

    /**
     * Returns the most recent profitability result for a fundo and season, if available.
     *
     * @param fundoId  the fundo identifier
     * @param seasonId the season identifier
     * @return an {@link Optional} with net profit as {@link Money}, or empty if not calculated
     */
    Optional<Money> fetchNetProfitByFundoAndSeason(Long fundoId, Long seasonId);
}