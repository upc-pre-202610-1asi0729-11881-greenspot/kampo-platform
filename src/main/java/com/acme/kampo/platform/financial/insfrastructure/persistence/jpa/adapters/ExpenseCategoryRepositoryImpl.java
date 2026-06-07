package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.adapters;


import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;
import com.acme.kampo.platform.financial.domain.repository.ExpenseCategoryRepository;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers.ExpenseCategoryPersistenceAssembler;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories.ExpenseCategoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link ExpenseCategoryRepository} contract using Spring Data JPA.
 * Note: ExpenseCategory is not an aggregate root — no domain events are published.
 */
@Repository
public class ExpenseCategoryRepositoryImpl implements ExpenseCategoryRepository {

    private final ExpenseCategoryJpaRepository jpaRepository;

    public ExpenseCategoryRepositoryImpl(ExpenseCategoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public ExpenseCategory save(ExpenseCategory category) {
        var entity = ExpenseCategoryPersistenceAssembler.toPersistenceFromDomain(category);
        var saved  = jpaRepository.save(entity);
        return ExpenseCategoryPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<ExpenseCategory> findById(Long id) {
        return jpaRepository.findById(id)
                .map(ExpenseCategoryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<ExpenseCategory> findAll() {
        return jpaRepository.findAll().stream()
                .map(ExpenseCategoryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.countByName(name) > 0;
    }

    @Override
    public boolean existsById(Long id) {
        return jpaRepository.existsById(id);
    }
}
