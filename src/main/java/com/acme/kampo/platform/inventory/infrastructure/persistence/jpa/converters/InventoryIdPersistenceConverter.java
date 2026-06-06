package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.converters;

import com.acme.kampo.platform.inventory.domain.model.valueObjects.InventoryId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link InventoryId} between its domain representation and a plain
 * {@code Long} column value in the database.
 */
@Converter(autoApply = false)
public class InventoryIdPersistenceConverter implements AttributeConverter<InventoryId, Long> {

    @Override
    public Long convertToDatabaseColumn(InventoryId attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public InventoryId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : InventoryId.of(dbData);
    }
}
