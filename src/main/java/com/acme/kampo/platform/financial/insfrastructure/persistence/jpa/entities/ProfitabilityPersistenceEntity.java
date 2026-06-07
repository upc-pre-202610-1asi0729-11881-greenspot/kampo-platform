package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities;


import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.financial.domain.model.aggregates.Profitability} aggregate.
 *
 * <p>Contains four embedded {@link MoneyPersistenceEmbeddable} fields, each with
 * distinct {@code @AttributeOverrides} to avoid column name conflicts.</p>
 *
 * <p>Translation handled by {@link com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ProfitabilityPersistenceEntity}.</p>
 */
@Setter
@Getter
@Entity
@Table(name = "profitabilities")
public class ProfitabilityPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "fundo_id", nullable = false)
    private Long fundoId;

    @Column(name = "season_id", nullable = false)
    private Long seasonId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "total_income_amount",   nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_income_currency", nullable = false, length = 3))
    })
    private MoneyPersistenceEmbeddable totalIncome;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "total_expenses_amount",   nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_expenses_currency", nullable = false, length = 3))
    })
    private MoneyPersistenceEmbeddable totalExpenses;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "total_sales_amount",   nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_sales_currency", nullable = false, length = 3))
    })
    private MoneyPersistenceEmbeddable totalSales;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "net_profit_amount",   nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "net_profit_currency", nullable = false, length = 3))
    })
    private MoneyPersistenceEmbeddable netProfit;

    @Column(nullable = false)
    private Double margin;

    @Column(name = "calculated_at", nullable = false)
    private LocalDateTime calculatedAt;

    public ProfitabilityPersistenceEntity() {}

}
