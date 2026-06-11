package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.organization.domain.model.aggregates.Fundo;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FundoId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;
import com.acme.kampo.platform.organization.domain.repositories.FundoRepository;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers.FundoPersistenceAssembler;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories.FundoJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link FundoRepository} contract using Spring Data JPA.
 * Note: Fundo does not publish domain events — no ApplicationEventPublisher needed.
 */
@Repository
public class FundoRepositoryImpl implements FundoRepository {

    private final FundoJpaRepository jpaRepository;

    public FundoRepositoryImpl(FundoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Fundo save(Fundo fundo) {
        var entity = FundoPersistenceAssembler.toPersistenceFromDomain(fundo);
        var saved  = jpaRepository.save(entity);
        return FundoPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<Fundo> findById(FundoId id) {
        return jpaRepository.findById(id.getValue())
                .map(FundoPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Fundo> findByOrganizationId(OrganizationId organizationId) {
        return jpaRepository.findByOrganizationId(organizationId.getValue()).stream()
                .map(FundoPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Fundo> findAll() {
        return jpaRepository.findAll().stream()
                .map(FundoPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(FundoId id) {
        return jpaRepository.existsById(id.getValue());
    }
}