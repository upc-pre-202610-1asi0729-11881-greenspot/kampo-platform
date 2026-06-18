package com.acme.kampo.platform.report.domain.model.queries;

import com.acme.kampo.platform.report.domain.model.valueobjects.ReportId;

public record GetRecommendationsByReportIdQuery(ReportId reportId) {

    public GetRecommendationsByReportIdQuery {
        if (reportId == null)
            throw new IllegalArgumentException("ReportId must not be null");
    }
}