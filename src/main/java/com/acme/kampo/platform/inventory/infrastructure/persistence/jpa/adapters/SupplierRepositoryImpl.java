package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.repositories.SupplierRepository;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.SupplierPersistenceAssembler;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories.SupplierJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link SupplierRepository} contract using Spring Data JPA.
 */
@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    private final SupplierJpaRepository     jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public SupplierRepositoryImpl(SupplierJpaRepository jpaRepository,
                                  ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Supplier save(Supplier supplier) {
        boolean isNew     = supplier.getId() == null;
        var entity        = SupplierPersistenceAssembler.toPersistenceFromDomain(supplier);
        var savedEntity   = jpaRepository.save(entity);
        var savedSupplier = SupplierPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedSupplier.domainEvents().forEach(eventPublisher::publishEvent);
            savedSupplier.clearDomainEvents();
        }
        return savedSupplier;
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return jpaRepository.findById(id)
                .map(SupplierPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Supplier> findByEmail(String email) {
        return jpaRepository.findByEmail(email)
                .map(SupplierPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Supplier> findAll() {
        return jpaRepository.findAll().stream()
                .map(SupplierPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaRepository.countByEmail(email) > 0;
    }
}