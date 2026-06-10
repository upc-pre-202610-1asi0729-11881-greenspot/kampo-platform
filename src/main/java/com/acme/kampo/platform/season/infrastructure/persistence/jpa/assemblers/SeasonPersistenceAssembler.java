package com.acme.kampo.platform.season.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.domain.model.valueObjects.CropId;
import com.acme.kampo.platform.season.domain.model.valueObjects.DateRange;
import com.acme.kampo.platform.season.domain.model.valueObjects.FieldId;
import com.acme.kampo.platform.season.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.season.infrastructure.persistence.jpa.entities.SeasonPersistenceEntity;

public class SeasonPersistenceAssembler {

    public static SeasonPersistenceEntity toEntity(Season season) {
        SeasonPersistenceEntity entity = new SeasonPersistenceEntity();
        if (season.getId() != null)
            entity.setId(season.getId().getValue());
        entity.setFieldId(season.getFieldId().getValue());
        if (season.getCropId() != null)
            entity.setCropId(season.getCropId().getValue());
        entity.setCropName(season.getCropName());
        entity.setStatus(season.getStatus());
        entity.setStartedAt(season.getStartedAt());
        entity.setEndedAt(season.getEndedAt());
        return entity;
    }

    public static Season toDomain(SeasonPersistenceEntity entity) {
        Season season = Season.reconstitute();
        season.setId(SeasonId.of(entity.getId()));
        season.setFieldId(FieldId.of(entity.getFieldId()));
        if (entity.getCropId() != null)
            season.setCropId(CropId.of(entity.getCropId()));
        season.setCropName(entity.getCropName());
        season.setStatus(entity.getStatus());
        season.setDateRange(DateRange.of(entity.getStartedAt(), entity.getEndedAt()));
        return season;
    }
}