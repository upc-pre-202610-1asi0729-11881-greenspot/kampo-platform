package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities;


import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.financial.domain.model.aggregates.Expense} aggregate.
 * Extends {@link AuditableAbstractPersistenceEntity} to inherit {@code id}, {@code createdAt} and {@code updatedAt}.
 * Translation handled by {@link com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.ExpensePersistenceEntity}.
 */
@Setter
@Getter
@Entity
@Table(name = "expenses")
public class ExpensePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String description;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "amount",   nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "currency", nullable = false, length = 3))
    })
    private MoneyPersistenceEmbeddable amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private ExpenseType type;

    @Column(name = "category_id", nullable = false)
    private Long categoryId;

    @Column(name = "fundo_id", nullable = false)
    private Long fundoId;

    @Column(nullable = false)
    private LocalDate date;

    public ExpensePersistenceEntity() {}

}

