package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.adapters;


import com.acme.kampo.platform.financial.domain.model.aggregates.Income;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.IncomeId;
import com.acme.kampo.platform.financial.domain.repository.IncomeRepository;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers.IncomePersistenceAssembler;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories.IncomeJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link IncomeRepository} contract using Spring Data JPA.
 */
@Repository
public class IncomeRepositoryImpl implements IncomeRepository {

    private final IncomeJpaRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public IncomeRepositoryImpl(IncomeJpaRepository jpaRepository,
                                ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Income save(Income income) {
        boolean isNew = income.getId() == null;
        var entity    = IncomePersistenceAssembler.toPersistenceFromDomain(income);
        var saved     = jpaRepository.save(entity);
        var savedIncome = IncomePersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedIncome.domainEvents().forEach(eventPublisher::publishEvent);
            savedIncome.clearDomainEvents();
        }
        return savedIncome;
    }

    @Override
    public Optional<Income> findById(IncomeId id) {
        return jpaRepository.findById(id.getValue())
                .map(IncomePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Income> findByFundoId(FundoId fundoId) {
        return jpaRepository.findByFundoId(fundoId.getValue()).stream()
                .map(IncomePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Income> findAll() {
        return jpaRepository.findAll().stream()
                .map(IncomePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void delete(IncomeId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsById(IncomeId id) {
        return jpaRepository.existsById(id.getValue());
    }
}
