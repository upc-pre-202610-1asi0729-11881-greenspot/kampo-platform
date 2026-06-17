package com.acme.kampo.platform.field.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities.ObservationPersistenceEntity;

/**
 * Static assembler between the {@link Observation} aggregate and its JPA persistence representation.
 */
public final class ObservationPersistenceAssembler {

    private ObservationPersistenceAssembler() {}

    public static Observation toDomainFromPersistence(ObservationPersistenceEntity entity) {
        return new Observation(
                entity.getId(),
                entity.getFieldVisitId(),
                entity.getNotes(),
                entity.getPestName(),
                entity.getPestSeverity(),
                entity.getDiseaseName(),
                entity.getDiseaseSeverity(),
                entity.getRecommendation(),
                entity.getEvidenceUrl());
    }

    public static ObservationPersistenceEntity toPersistenceFromDomain(Observation observation) {
        var entity = new ObservationPersistenceEntity();
        entity.setId(observation.getId() != null ? observation.getId().getValue() : null);
        entity.setFieldVisitId(observation.getFieldVisitId().getValue());
        entity.setNotes(observation.getNotes());
        entity.setPestName(observation.getPestName());
        entity.setPestSeverity(observation.getPestSeverity());
        entity.setDiseaseName(observation.getDiseaseName());
        entity.setDiseaseSeverity(observation.getDiseaseSeverity());
        entity.setRecommendation(observation.getRecommendation());
        entity.setEvidenceUrl(observation.getEvidenceUrl());
        return entity;
    }
}