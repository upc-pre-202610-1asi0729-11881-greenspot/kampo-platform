package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers;


import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ExpensePersistenceEntity;

/**
 * Static assembler between the {@link Expense} aggregate and its JPA persistence representation.
 */
public final class ExpensePersistenceAssembler {

    private ExpensePersistenceAssembler() {}

    public static Expense toDomainFromPersistence(ExpensePersistenceEntity entity) {
        return new Expense(
                entity.getId(),
                entity.getDescription(),
                toDomainFromPersistence(entity.getAmount()),
                entity.getType(),
                entity.getCategoryId(),
                entity.getFundoId(),
                entity.getDate());
    }

    public static ExpensePersistenceEntity toPersistenceFromDomain(Expense expense) {
        var entity = new ExpensePersistenceEntity();
        entity.setId(expense.getId() != null ? expense.getId().getValue() : null);
        entity.setDescription(expense.getDescription());
        entity.setAmount(toPersistenceFromDomain(expense.getAmount()));
        entity.setType(expense.getType());
        entity.setCategoryId(expense.getCategoryId());
        entity.setFundoId(expense.getFundoId().getValue());
        entity.setDate(expense.getDate());
        return entity;
    }

    private static Money toDomainFromPersistence(MoneyPersistenceEmbeddable value) {
        return value == null ? null : Money.of(value.getAmount(), value.getCurrency());
    }

    private static MoneyPersistenceEmbeddable toPersistenceFromDomain(Money value) {
        return value == null ? null : new MoneyPersistenceEmbeddable(value.amount(), value.currency());
    }
}