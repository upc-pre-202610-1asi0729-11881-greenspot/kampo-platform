package com.acme.kampo.platform.season.infrastructure.persistence.jpa.converters;

import com.acme.kampo.platform.season.domain.model.valueobjects.SeasonId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class SeasonIdPersistenceConverter implements AttributeConverter<SeasonId, Long> {

    @Override
    public Long convertToDatabaseColumn(SeasonId attribute){
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public SeasonId convertToEntityAttribute(Long dbData){
        return dbData == null ? null : SeasonId.of(dbData);
    }

}

