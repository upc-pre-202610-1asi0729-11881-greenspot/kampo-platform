package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.adapters;


import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ExpenseId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.repository.ExpenseRepository;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers.ExpensePersistenceAssembler;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories.ExpenseJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link ExpenseRepository} contract using Spring Data JPA.
 */
@Repository
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final ExpenseJpaRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ExpenseRepositoryImpl(ExpenseJpaRepository jpaRepository,
                                 ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Expense save(Expense expense) {
        boolean isNew  = expense.getId() == null;
        var entity     = ExpensePersistenceAssembler.toPersistenceFromDomain(expense);
        var saved      = jpaRepository.save(entity);
        var savedExpense = ExpensePersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedExpense.domainEvents().forEach(eventPublisher::publishEvent);
            savedExpense.clearDomainEvents();
        }
        return savedExpense;
    }

    @Override
    public Optional<Expense> findById(ExpenseId id) {
        return jpaRepository.findById(id.getValue())
                .map(ExpensePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Expense> findByFundoId(FundoId fundoId) {
        return jpaRepository.findByFundoId(fundoId.getValue()).stream()
                .map(ExpensePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Expense> findByType(ExpenseType type) {
        return jpaRepository.findByType(type).stream()
                .map(ExpensePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Expense> findAll() {
        return jpaRepository.findAll().stream()
                .map(ExpensePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void delete(ExpenseId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(ExpenseId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
