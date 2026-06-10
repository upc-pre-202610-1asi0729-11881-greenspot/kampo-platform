package com.acme.kampo.platform.report.interfaces.rest.transform;

import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.interfaces.rest.resources.RecommendationResource;

public class RecommendationResourceAssembler {

    public static RecommendationResource toResource(Recommendation recommendation) {
        return new RecommendationResource(
                recommendation.getId().getValue(),
                recommendation.getPriority(),
                recommendation.getStatus(),
                recommendation.getCreatedAt(),
                recommendation.getImplementedAt(),
                recommendation.getReportId().getValue());
    }
}
