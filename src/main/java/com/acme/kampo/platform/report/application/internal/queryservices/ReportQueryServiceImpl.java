package com.acme.kampo.platform.report.application.internal.queryservices;

import com.acme.kampo.platform.report.application.queryservices.ReportQueryService;
import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.domain.model.queries.GetAllReportsQuery;
import com.acme.kampo.platform.report.domain.model.queries.GetRecommendationsByReportIdQuery;
import com.acme.kampo.platform.report.domain.model.queries.GetReportByIdQuery;
import com.acme.kampo.platform.report.domain.repositories.RecommendationRepository;
import com.acme.kampo.platform.report.domain.repositories.ReportRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@Transactional(readOnly = true)
public class ReportQueryServiceImpl implements ReportQueryService {

    private final ReportRepository reportRepository;
    private final RecommendationRepository recommendationRepository;

    public ReportQueryServiceImpl(ReportRepository reportRepository,
                                  RecommendationRepository recommendationRepository) {
        this.reportRepository = reportRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    public Optional<Report> handle(GetReportByIdQuery query) {
        log.debug("Querying report by id={}", query.reportId().getValue());
        return reportRepository.findById(query.reportId());
    }

    @Override
    public List<Report> handle(GetAllReportsQuery query) {
        log.debug("Querying all reports");
        return reportRepository.findAll();
    }

    @Override
    public List<Recommendation> handle(GetRecommendationsByReportIdQuery query) {
        log.debug("Querying recommendations by reportId={}", query.reportId().getValue());
        return recommendationRepository.findByReportId(query.reportId());
    }
}
