package com.acme.kampo.platform.report.domain.repositories;

import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.domain.model.valueobjects.RecommendationId;
import com.acme.kampo.platform.report.domain.model.valueobjects.ReportId;

import java.util.List;
import java.util.Optional;

public interface RecommendationRepository {

    Optional<Recommendation> findById(RecommendationId recommendationId);
    List<Recommendation> findByReportId(ReportId reportId);
    Recommendation save(Recommendation recommendation);
}
