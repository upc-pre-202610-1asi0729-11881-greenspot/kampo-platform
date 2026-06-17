package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.converters;

import com.acme.kampo.platform.inventory.domain.model.valueObjects.SupplierId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link SupplierId} between its domain representation and a plain
 * {@code Long} column value in the database.
 */
@Converter(autoApply = false)
public class SupplierIdPersistenceConverter implements AttributeConverter<SupplierId, Long> {

    @Override
    public Long convertToDatabaseColumn(SupplierId attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public SupplierId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : SupplierId.of(dbData);
    }
}