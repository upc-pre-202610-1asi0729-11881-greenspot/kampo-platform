package com.acme.kampo.platform.financial.application.acl;


import com.acme.kampo.platform.financial.application.queryservices.ExpenseQueryService;
import com.acme.kampo.platform.financial.application.queryservices.IncomeQueryService;
import com.acme.kampo.platform.financial.application.queryservices.ProfitabilityQueryService;
import com.acme.kampo.platform.financial.application.queryservices.SaleQueryService;
import com.acme.kampo.platform.financial.domain.model.queries.*;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.financial.interfaces.acl.FinancialContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link FinancialContextFacade}.
 *
 * <p>Aggregates query results into totals using {@link Money#add(Money)} reduction,
 * returning only primitive-friendly types to consuming bounded contexts.</p>
 */
@Service
public class FinancialContextFacadeImpl implements FinancialContextFacade {

    private static final String DEFAULT_CURRENCY = "PEN";

    private final ExpenseQueryService       expenseQueryService;
    private final IncomeQueryService        incomeQueryService;
    private final SaleQueryService          saleQueryService;
    private final ProfitabilityQueryService profitabilityQueryService;

    public FinancialContextFacadeImpl(
            ExpenseQueryService expenseQueryService,
            IncomeQueryService incomeQueryService,
            SaleQueryService saleQueryService,
            ProfitabilityQueryService profitabilityQueryService) {
        this.expenseQueryService       = expenseQueryService;
        this.incomeQueryService        = incomeQueryService;
        this.saleQueryService          = saleQueryService;
        this.profitabilityQueryService = profitabilityQueryService;
    }

    @Override
    public Money fetchExpenseTotalByFundo(Long fundoId) {
        return expenseQueryService
                .handle(new GetExpensesByFundoQuery(FundoId.of(fundoId)))
                .stream()
                .map(e -> e.getAmount())
                .reduce(Money.zero(DEFAULT_CURRENCY), Money::add);
    }

    @Override
    public Money fetchIncomeTotalByFundo(Long fundoId) {
        return incomeQueryService
                .handle(new GetIncomesByFundoQuery(FundoId.of(fundoId)))
                .stream()
                .map(i -> i.getAmount())
                .reduce(Money.zero(DEFAULT_CURRENCY), Money::add);
    }

    @Override
    public Money fetchSaleTotalByFundo(Long fundoId) {
        return saleQueryService
                .handle(new GetSalesByFundoQuery(FundoId.of(fundoId)))
                .stream()
                .filter(s -> !s.isCancelled())
                .map(s -> s.getTotalAmount())
                .reduce(Money.zero(DEFAULT_CURRENCY), Money::add);
    }

    @Override
    public Optional<Money> fetchNetProfitByFundoAndSeason(Long fundoId, Long seasonId) {
        return profitabilityQueryService
                .handle(new GetProfitabilityBySeasonQuery(FundoId.of(fundoId), SeasonId.of(seasonId)))
                .map(p -> p.getNetProfit());
    }
}
