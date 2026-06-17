package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.organization.domain.model.aggregates.Crop;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.CropPersistenceEntity;

/**
 * Static assembler between the {@link Crop} aggregate and its JPA persistence representation.
 */
public final class CropPersistenceAssembler {

    private CropPersistenceAssembler() {}

    public static Crop toDomainFromPersistence(CropPersistenceEntity entity) {
        return new Crop(entity.getId(), entity.getFieldId(), entity.getName(), entity.getPlantedAt());
    }

    public static CropPersistenceEntity toPersistenceFromDomain(Crop crop) {
        var entity = new CropPersistenceEntity();
        entity.setId(crop.getId() != null ? crop.getId().getValue() : null);
        entity.setFieldId(crop.getFieldId().getValue());
        entity.setName(crop.getName());
        entity.setPlantedAt(crop.getPlantedAt());
        return entity;
    }
}
