package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.organization.domain.model.aggregates.Crop;
import com.acme.kampo.platform.organization.domain.model.valueobjects.CropId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.organization.domain.repositories.CropRepository;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers.CropPersistenceAssembler;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories.CropJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link CropRepository} contract using Spring Data JPA.
 */
@Repository
public class CropRepositoryImpl implements CropRepository {

    private final CropJpaRepository jpaRepository;

    public CropRepositoryImpl(CropJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Crop save(Crop crop) {
        var entity = CropPersistenceAssembler.toPersistenceFromDomain(crop);
        var saved  = jpaRepository.save(entity);
        return CropPersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<Crop> findById(CropId id) {
        return jpaRepository.findById(id.getValue())
                .map(CropPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Crop> findByFieldId(FieldId fieldId) {
        return jpaRepository.findByFieldId(fieldId.getValue()).stream()
                .map(CropPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(CropId id) {
        return jpaRepository.existsById(id.getValue());
    }
}