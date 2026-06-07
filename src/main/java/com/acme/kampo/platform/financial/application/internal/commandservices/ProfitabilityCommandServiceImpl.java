package com.acme.kampo.platform.financial.application.internal.commandservices;


import com.acme.kampo.platform.financial.application.commandservices.ProfitabilityCommandService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.command.CalculateProfitabilityCommand;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.domain.repository.ExpenseRepository;
import com.acme.kampo.platform.financial.domain.repository.IncomeRepository;
import com.acme.kampo.platform.financial.domain.repository.ProfitabilityRepository;
import com.acme.kampo.platform.financial.domain.repository.SaleRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link ProfitabilityCommandService}.
 *
 * <p>Orchestrates the calculation by:
 * <ol>
 *   <li>Fetching all income, expense and sale entries for the fundo.</li>
 *   <li>Summing each group into a {@link Money} total (same currency assumed).</li>
 *   <li>Delegating net profit and margin computation to the {@link Profitability} aggregate.</li>
 *   <li>Persisting the result via {@link ProfitabilityRepository}.</li>
 * </ol>
 *
 * <p>This service does not perform currency conversion — all entries for a fundo
 * are assumed to share the same currency. Cross-currency support would require
 * an exchange-rate service, which is out of scope for this bounded context.</p>
 */
@Service
@Transactional
public class ProfitabilityCommandServiceImpl implements ProfitabilityCommandService {

    private static final String DEFAULT_CURRENCY = "PEN";

    private final ExpenseRepository expenseRepository;
    private final IncomeRepository incomeRepository;
    private final SaleRepository saleRepository;
    private final ProfitabilityRepository profitabilityRepository;

    public ProfitabilityCommandServiceImpl(
            ExpenseRepository expenseRepository,
            IncomeRepository incomeRepository,
            SaleRepository saleRepository,
            ProfitabilityRepository profitabilityRepository) {
        this.expenseRepository       = expenseRepository;
        this.incomeRepository        = incomeRepository;
        this.saleRepository          = saleRepository;
        this.profitabilityRepository = profitabilityRepository;
    }

    @Override
    public Result<Profitability, ApplicationError> handle(CalculateProfitabilityCommand command) {
        try {
            var fundoId = FundoId.of(command.fundoId());

            var totalIncome = incomeRepository
                    .findByFundoId(fundoId).stream()
                    .map(i -> i.getAmount())
                    .reduce(Money.zero(DEFAULT_CURRENCY), Money::add);

            var totalExpenses = expenseRepository
                    .findByFundoId(fundoId).stream()
                    .map(e -> e.getAmount())
                    .reduce(Money.zero(DEFAULT_CURRENCY), Money::add);

            var totalSales = saleRepository
                    .findByFundoId(fundoId).stream()
                    .filter(s -> !s.isCancelled())
                    .map(s -> s.getTotalAmount())
                    .reduce(Money.zero(DEFAULT_CURRENCY), Money::add);

            var profitability = profitabilityRepository.save(
                    new Profitability(command, totalIncome, totalExpenses, totalSales));

            return Result.success(profitability);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "ProfitabilityCommandService.handle", e.getMessage()));
        }
    }
}
