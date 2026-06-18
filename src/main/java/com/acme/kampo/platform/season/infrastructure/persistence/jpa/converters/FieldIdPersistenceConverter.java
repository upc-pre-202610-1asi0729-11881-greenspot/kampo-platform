package com.acme.kampo.platform.season.infrastructure.persistence.jpa.converters;

import com.acme.kampo.platform.season.domain.model.valueobjects.FieldId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class FieldIdPersistenceConverter implements AttributeConverter <FieldId,Long> {

    @Override
    public Long convertToDatabaseColumn(FieldId attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public FieldId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : FieldId.of(dbData);
    }
}
