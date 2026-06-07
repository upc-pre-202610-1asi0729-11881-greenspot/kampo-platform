package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers;


import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.IncomePersistenceEntity;

/**
 * Static assembler between the {@link Income} aggregate and its JPA persistence representation.
 */
public final class IncomePersistenceAssembler {

    private IncomePersistenceAssembler() {}

    public static Income toDomainFromPersistence(IncomePersistenceEntity entity) {
        return new Income(
                entity.getId(),
                entity.getDescription(),
                toDomainFromPersistence(entity.getAmount()),
                entity.getFundoId(),
                entity.getDate());
    }

    public static IncomePersistenceEntity toPersistenceFromDomain(Income income) {
        var entity = new IncomePersistenceEntity();
        entity.setId(income.getId() != null ? income.getId().getValue() : null);
        entity.setDescription(income.getDescription());
        entity.setAmount(toPersistenceFromDomain(income.getAmount()));
        entity.setFundoId(income.getFundoId().getValue());
        entity.setDate(income.getDate());
        return entity;
    }

    private static Money toDomainFromPersistence(MoneyPersistenceEmbeddable value) {
        return value == null ? null : Money.of(value.getAmount(), value.getCurrency());
    }

    private static MoneyPersistenceEmbeddable toPersistenceFromDomain(Money value) {
        return value == null ? null : new MoneyPersistenceEmbeddable(value.amount(), value.currency());
    }
}
