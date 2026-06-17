package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.organization.domain.model.aggregates.Organization;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;
import com.acme.kampo.platform.organization.domain.repositories.OrganizationRepository;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers.OrganizationPersistenceAssembler;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories.OrganizationJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link OrganizationRepository} contract using Spring Data JPA.
 */
@Repository
public class OrganizationRepositoryImpl implements OrganizationRepository {

    private final OrganizationJpaRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrganizationRepositoryImpl(OrganizationJpaRepository jpaRepository,
                                      ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Organization save(Organization organization) {
        boolean isNew = organization.getId() == null;
        var entity    = OrganizationPersistenceAssembler.toPersistenceFromDomain(organization);
        var saved     = jpaRepository.save(entity);
        var domain    = OrganizationPersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            domain.domainEvents().forEach(eventPublisher::publishEvent);
            domain.clearDomainEvents();
        }
        return domain;
    }

    @Override
    public Optional<Organization> findById(OrganizationId id) {
        return jpaRepository.findById(id.getValue())
                .map(OrganizationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Organization> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(OrganizationPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Organization> findAll() {
        return jpaRepository.findAll().stream()
                .map(OrganizationPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public void delete(OrganizationId id) {
        jpaRepository.deleteById(id.getValue());
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.countByName(name) > 0;
    }

    @Override
    public boolean existsById(OrganizationId id) {
        return jpaRepository.existsById(id.getValue());
    }
}