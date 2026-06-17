package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers;


import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ProfitabilityPersistenceEntity;

/**
 * Static assembler between the {@link Profitability} aggregate and its JPA persistence representation.
 * Handles four {@link Money} fields: totalIncome, totalExpenses, totalSales and netProfit.
 */
public final class ProfitabilityPersistenceAssembler {

    private ProfitabilityPersistenceAssembler() {}

    public static Profitability toDomainFromPersistence(ProfitabilityPersistenceEntity entity) {
        return new Profitability(
                entity.getId(),
                entity.getFundoId(),
                entity.getSeasonId(),
                toDomain(entity.getTotalIncome()),
                toDomain(entity.getTotalExpenses()),
                toDomain(entity.getTotalSales()),
                toDomain(entity.getNetProfit()),
                entity.getMargin(),
                entity.getCalculatedAt());
    }

    public static ProfitabilityPersistenceEntity toPersistenceFromDomain(Profitability profitability) {
        var entity = new ProfitabilityPersistenceEntity();
        entity.setId(profitability.getId() != null ? profitability.getId().getValue() : null);
        entity.setFundoId(profitability.getFundoId().getValue());
        entity.setSeasonId(profitability.getSeasonId().getValue());
        entity.setTotalIncome(toPersistence(profitability.getTotalIncome()));
        entity.setTotalExpenses(toPersistence(profitability.getTotalExpenses()));
        entity.setTotalSales(toPersistence(profitability.getTotalSales()));
        entity.setNetProfit(toPersistence(profitability.getNetProfit()));
        entity.setMargin(profitability.getMargin());
        entity.setCalculatedAt(profitability.getCalculatedAt());
        return entity;
    }

    private static Money toDomain(MoneyPersistenceEmbeddable value) {
        return value == null ? null : Money.of(value.getAmount(), value.getCurrency());
    }

    private static MoneyPersistenceEmbeddable toPersistence(Money value) {
        return value == null ? null : new MoneyPersistenceEmbeddable(value.amount(), value.currency());
    }
}
