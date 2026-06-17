package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.organization.domain.model.aggregates.Field;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.FieldPersistenceEntity;

/**
 * Static assembler between the {@link Field} aggregate and its JPA persistence representation.
 */
public final class FieldPersistenceAssembler {

    private FieldPersistenceAssembler() {}

    public static Field toDomainFromPersistence(FieldPersistenceEntity entity) {
        return new Field(entity.getId(), entity.getFundoId(), entity.getName(), entity.getAreaSqm());
    }

    public static FieldPersistenceEntity toPersistenceFromDomain(Field field) {
        var entity = new FieldPersistenceEntity();
        entity.setId(field.getId() != null ? field.getId().getValue() : null);
        entity.setFundoId(field.getFundoId().getValue());
        entity.setName(field.getName());
        entity.setAreaSqm(field.getAreaSqm());
        return entity;
    }
}
