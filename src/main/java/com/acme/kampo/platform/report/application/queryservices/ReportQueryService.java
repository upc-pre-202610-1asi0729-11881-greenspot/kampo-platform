package com.acme.kampo.platform.report.application.queryservices;

import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.domain.model.queries.GetAllReportsQuery;
import com.acme.kampo.platform.report.domain.model.queries.GetRecommendationsByReportIdQuery;
import com.acme.kampo.platform.report.domain.model.queries.GetReportByIdQuery;

import java.util.List;
import java.util.Optional;

public interface ReportQueryService {

    Optional<Report> handle(GetReportByIdQuery query);
    List<Report> handle(GetAllReportsQuery query);
    List<Recommendation> handle(GetRecommendationsByReportIdQuery query);
}
