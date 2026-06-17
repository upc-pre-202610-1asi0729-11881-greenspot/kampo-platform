package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers;


import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ExpenseCategoryPersistenceEntity;

/**
 * Static assembler between the {@link ExpenseCategory} entity and its JPA persistence representation.
 */
public final class ExpenseCategoryPersistenceAssembler {

    private ExpenseCategoryPersistenceAssembler() {}

    public static ExpenseCategory toDomainFromPersistence(ExpenseCategoryPersistenceEntity entity) {
        return new ExpenseCategory(entity.getId(), entity.getName(), entity.getDescription());
    }

    public static ExpenseCategoryPersistenceEntity toPersistenceFromDomain(ExpenseCategory category) {
        var entity = new ExpenseCategoryPersistenceEntity();
        entity.setId(category.getId());
        entity.setName(category.getName());
        entity.setDescription(category.getDescription());
        return entity;
    }
}