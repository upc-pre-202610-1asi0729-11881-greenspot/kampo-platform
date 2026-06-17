package com.acme.kampo.platform.field.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities.FieldVisitPersistenceEntity;

/**
 * Static assembler between the {@link FieldVisit} aggregate and its JPA persistence representation.
 */
public final class FieldVisitPersistenceAssembler {

    private FieldVisitPersistenceAssembler() {}

    public static FieldVisit toDomainFromPersistence(FieldVisitPersistenceEntity entity) {
        return new FieldVisit(
                entity.getId(),
                entity.getFieldId(),
                entity.getAgentId(),
                entity.getScheduledAt(),
                entity.getDoneAt(),
                entity.getStatus());
    }

    public static FieldVisitPersistenceEntity toPersistenceFromDomain(FieldVisit fieldVisit) {
        var entity = new FieldVisitPersistenceEntity();
        entity.setId(fieldVisit.getId() != null ? fieldVisit.getId().getValue() : null);
        entity.setFieldId(fieldVisit.getFieldId().getValue());
        entity.setAgentId(fieldVisit.getAgentId());
        entity.setScheduledAt(fieldVisit.getScheduledAt());
        entity.setDoneAt(fieldVisit.getDoneAt());
        entity.setStatus(fieldVisit.getStatus());
        return entity;
    }
}