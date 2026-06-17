package com.acme.kampo.platform.financial.domain.model.aggregates;


import com.acme.kampo.platform.financial.domain.model.command.CalculateProfitabilityCommand;
import com.acme.kampo.platform.financial.domain.model.events.ProfitabilityCalculatedEvent;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ProfitabilityId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

/**
 * Aggregate root representing the calculated profitability of a fundo for a given season.
 *
 * <p>This replaces the original {@code ProfitabilityCalculatorService} domain service.
 * The calculation logic lives here — inside the aggregate — following DDD principles:
 * behaviour belongs with the data it operates on.</p>
 *
 * <p>The command service fetches the period totals (income, expenses, sales) and passes
 * them to the creation constructor, which computes {@code netProfit} and {@code margin}
 * internally. The result is then persisted and a {@link ProfitabilityCalculatedEvent}
 * is published.</p>
 *
 * <p>Formula:
 * <pre>
 *   netProfit = totalIncome + totalSales - totalExpenses
 *   margin    = (netProfit / (totalIncome + totalSales)) × 100   [capped at 0 if revenue is zero]
 * </pre>
 * </p>
 */
@Getter
public class Profitability extends AbstractDomainAggregateRoot<Profitability> {

    private ProfitabilityId id;
    private FundoId fundoId;
    private SeasonId seasonId;
    private Money           totalIncome;
    private Money           totalExpenses;
    private Money totalSales;
    private Money           netProfit;
    private Double          margin;
    private LocalDateTime calculatedAt;

    /** Required by JPA proxy — do not use directly. */
    protected Profitability() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * re-running the calculation.
     * Used exclusively by the persistence assembler.
     */
    public Profitability(Long id, Long fundoId, Long seasonId,
                         Money totalIncome, Money totalExpenses, Money totalSales,
                         Money netProfit, Double margin, LocalDateTime calculatedAt) {
        this.id            = ProfitabilityId.of(id);
        this.fundoId       = FundoId.of(fundoId);
        this.seasonId      = SeasonId.of(seasonId);
        this.totalIncome   = totalIncome;
        this.totalExpenses = totalExpenses;
        this.totalSales    = totalSales;
        this.netProfit     = netProfit;
        this.margin        = margin;
        this.calculatedAt  = calculatedAt;
    }

    /**
     * Calculation constructor — computes {@code netProfit} and {@code margin} from
     * the period totals and registers a {@link ProfitabilityCalculatedEvent}.
     *
     * @param command       the calculation command carrying fundo, season and period
     * @param totalIncome   sum of all income entries for the period
     * @param totalExpenses sum of all expense entries for the period
     * @param totalSales    sum of all sale amounts for the period
     */
    public Profitability(CalculateProfitabilityCommand command,
                         Money totalIncome, Money totalExpenses, Money totalSales) {
        this.fundoId       = FundoId.of(command.fundoId());
        this.seasonId      = SeasonId.of(command.seasonId());
        this.totalIncome   = totalIncome;
        this.totalExpenses = totalExpenses;
        this.totalSales    = totalSales;
        this.netProfit     = computeNetProfit(totalIncome, totalSales, totalExpenses);
        this.margin        = computeMargin(this.netProfit, totalIncome, totalSales);
        this.calculatedAt  = LocalDateTime.now();
        registerDomainEvent(new ProfitabilityCalculatedEvent(this));
    }

    // ── Calculation logic (private — encapsulated in the aggregate) ───────────

    /**
     * netProfit = totalIncome + totalSales - totalExpenses
     */
    private static Money computeNetProfit(Money income, Money sales, Money expenses) {
        return income.add(sales).subtract(expenses);
    }

    /**
     * margin = netProfit / (totalIncome + totalSales) × 100
     * Returns 0.0 when total revenue is zero to avoid division by zero.
     */
    private static Double computeMargin(Money netProfit, Money income, Money sales) {
        var totalRevenue = income.add(sales);
        if (totalRevenue.isZero()) return 0.0;
        return netProfit.amount()
                .divide(totalRevenue.amount(), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100))
                .doubleValue();
    }

    public Profitability reconstitute(Long rawId) {
        this.id = ProfitabilityId.of(rawId);
        return this;
    }
}
