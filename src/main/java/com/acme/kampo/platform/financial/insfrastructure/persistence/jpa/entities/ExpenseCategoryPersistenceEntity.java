package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities;


import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory} entity.
 * Translation handled by {@link com.acme.kampo.platform.financial.infrastructure.persistence.jpa.assemblers.ExpenseCategoryPersistenceAssembler}.
 */
@Setter
@Getter
@Entity
@Table(name = "expense_categories")
public class ExpenseCategoryPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column
    private String description;

    public ExpenseCategoryPersistenceEntity() {}

}
