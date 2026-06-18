package com.acme.kampo.platform.season.infrastructure.persistence.jpa.converters;

import com.acme.kampo.platform.season.domain.model.valueobjects.CropId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class CropIdPersistenceConverter implements AttributeConverter<CropId,Long> {

    @Override
    public Long convertToDatabaseColumn(CropId attribute){
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public CropId convertToEntityAttribute(Long dbData){
        return dbData == null ? null : CropId.of(dbData);
    }

}
