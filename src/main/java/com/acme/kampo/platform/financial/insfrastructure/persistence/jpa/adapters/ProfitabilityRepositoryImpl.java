package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.adapters;


import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ProfitabilityId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.financial.domain.repository.ProfitabilityRepository;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers.ProfitabilityPersistenceAssembler;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.repositories.ProfitabilityJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link ProfitabilityRepository} contract using Spring Data JPA.
 */
@Repository
public class ProfitabilityRepositoryImpl implements ProfitabilityRepository {

    private final ProfitabilityJpaRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ProfitabilityRepositoryImpl(ProfitabilityJpaRepository jpaRepository,
                                       ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Profitability save(Profitability profitability) {
        boolean isNew = profitability.getId() == null;
        var entity    = ProfitabilityPersistenceAssembler.toPersistenceFromDomain(profitability);
        var saved     = jpaRepository.save(entity);
        var savedProfitability = ProfitabilityPersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedProfitability.domainEvents().forEach(eventPublisher::publishEvent);
            savedProfitability.clearDomainEvents();
        }
        return savedProfitability;
    }

    @Override
    public Optional<Profitability> findById(ProfitabilityId id) {
        return jpaRepository.findById(id.getValue())
                .map(ProfitabilityPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Profitability> findByFundoIdAndSeasonId(FundoId fundoId, SeasonId seasonId) {
        if (seasonId == null) return Optional.empty();
        return jpaRepository.findTopByFundoIdAndSeasonId(fundoId.getValue(), seasonId.getValue())
                .map(ProfitabilityPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Profitability> findHistoryByFundoId(FundoId fundoId) {
        return jpaRepository.findHistoryByFundoId(fundoId.getValue()).stream()
                .map(ProfitabilityPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}
