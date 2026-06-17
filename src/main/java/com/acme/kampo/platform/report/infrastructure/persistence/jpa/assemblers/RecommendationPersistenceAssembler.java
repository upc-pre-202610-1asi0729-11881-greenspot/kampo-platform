package com.acme.kampo.platform.report.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.domain.model.valueObjects.RecommendationId;
import com.acme.kampo.platform.report.domain.model.valueObjects.ReportId;
import com.acme.kampo.platform.report.infrastructure.persistence.jpa.entities.RecommendationPersistenceEntity;

public class RecommendationPersistenceAssembler {

    public static RecommendationPersistenceEntity toEntity(Recommendation recommendation) {
        RecommendationPersistenceEntity entity = new RecommendationPersistenceEntity();
        if (recommendation.getId() != null)
            entity.setId(recommendation.getId().getValue());
        entity.setPriority(recommendation.getPriority());
        entity.setStatus(recommendation.getStatus());
        if (recommendation.getImplementedAt() != null)
            entity.setImplementedAt(java.util.Date.from(
                    recommendation.getImplementedAt()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toInstant()));
        entity.setReportId(recommendation.getReportId().getValue());
        return entity;
    }

    public static Recommendation toDomain(RecommendationPersistenceEntity entity) {
        Recommendation recommendation = Recommendation.reconstitute();
        recommendation.setId(RecommendationId.of(entity.getId()));
        recommendation.setPriority(entity.getPriority());
        recommendation.setStatus(entity.getStatus());
        if (entity.getImplementedAt() != null)
            recommendation.setImplementedAt(
                    entity.getImplementedAt().toInstant()
                            .atZone(java.time.ZoneId.systemDefault())
                            .toLocalDateTime());
        recommendation.setReportId(ReportId.of(entity.getReportId()));
        return recommendation;
    }
}
