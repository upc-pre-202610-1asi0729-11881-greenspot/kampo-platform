package com.acme.kampo.platform.report.domain.model.commands;

import com.acme.kampo.platform.report.domain.model.valueobjects.RecommendationId;

public record ImplementRecommendationCommand(RecommendationId recommendationId) {

    public ImplementRecommendationCommand {
        if (recommendationId == null)
            throw new IllegalArgumentException("RecommendationId must not be null");
    }
}
