package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.converters;

import com.acme.kampo.platform.inventory.domain.model.valueObjects.OrderId;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

/**
 * Converts {@link OrderId} between its domain representation and a plain
 * {@code Long} column value in the database.
 */
@Converter(autoApply = false)
public class OrderIdPersistenceConverter implements AttributeConverter<OrderId, Long> {

    @Override
    public Long convertToDatabaseColumn(OrderId attribute) {
        return attribute == null ? null : attribute.getValue();
    }

    @Override
    public OrderId convertToEntityAttribute(Long dbData) {
        return dbData == null ? null : OrderId.of(dbData);
    }
}

