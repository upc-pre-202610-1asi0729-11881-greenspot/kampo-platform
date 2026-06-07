package com.acme.kampo.platform.financial.application.commandservices;


import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.command.CalculateProfitabilityCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Profitability} write operations.
 *
 * <p>The implementation fetches period totals from the expense, income and sale
 * repositories, then delegates the net profit and margin calculation to the
 * {@link Profitability} aggregate constructor.</p>
 */
public interface ProfitabilityCommandService {

    /**
     * Calculates and persists profitability for a fundo and season period.
     *
     * @param command the calculation command carrying fundo, season and date range
     * @return {@link Result} with the calculated {@link Profitability} or an {@link ApplicationError}
     */
    Result<Profitability, ApplicationError> handle(CalculateProfitabilityCommand command);
}