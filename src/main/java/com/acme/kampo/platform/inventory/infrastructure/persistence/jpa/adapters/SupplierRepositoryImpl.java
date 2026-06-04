package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.repositories.SupplierRepository;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories.SupplierJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link SupplierRepository} contract
 * using Spring Data JPA.
 */
@Repository
public class SupplierRepositoryImpl implements SupplierRepository {

    private final SupplierJpaRepository jpaRepository;

    public SupplierRepositoryImpl(SupplierJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Supplier save(Supplier supplier) {
        var entity = SupplierPersistenceEntity.fromDomainModel(supplier);
        var saved  = jpaRepository.save(entity);
        return supplier.reconstitute(saved.getId());
    }

    @Override
    public Optional<Supplier> findById(Long id) {
        return jpaRepository.findById(id)
                .map(SupplierPersistenceEntity::toDomainModel);
    }

    @Override
    public List<Supplier> findAll() {
        return jpaRepository.findAll().stream()
                .map(SupplierPersistenceEntity::toDomainModel)
                .toList();
    }
}
