package com.acme.kampo.platform.report.application.internal.commandservices;

import com.acme.kampo.platform.report.application.commandservices.ReportCommandService;
import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.domain.model.commands.GenerateRecommendationCommand;
import com.acme.kampo.platform.report.domain.model.commands.GenerateReportCommand;
import com.acme.kampo.platform.report.domain.model.commands.ImplementRecommendationCommand;
import com.acme.kampo.platform.report.domain.repositories.ReportRepository;
import com.acme.kampo.platform.report.domain.repositories.RecommendationRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class ReportCommandServiceImpl implements ReportCommandService {

    private final ReportRepository reportRepository;
    private final RecommendationRepository recommendationRepository;

    public ReportCommandServiceImpl(ReportRepository reportRepository,
                                    RecommendationRepository recommendationRepository) {
        this.reportRepository = reportRepository;
        this.recommendationRepository = recommendationRepository;
    }

    @Override
    @Transactional
    public Result<Report, ApplicationError> handle(GenerateReportCommand command) {
        try {
            var report = new Report(command);
            var saved = reportRepository.save(report);
            log.info("Report generated: type={}, userId={}, seasonId={}",
                    command.type(), command.userId().getValue(), command.seasonId().getValue());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Report", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public Result<Recommendation, ApplicationError> handle(GenerateRecommendationCommand command) {
        try {
            var reportOpt = reportRepository.findById(command.reportId());
            if (reportOpt.isEmpty())
                return Result.failure(ApplicationError.notFound(
                        "Report", command.reportId().getValue().toString()));
            var recommendation = new Recommendation(command);
            var saved = recommendationRepository.save(recommendation);
            log.info("Recommendation generated: priority={}, reportId={}",
                    command.priority(), command.reportId().getValue());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.validationError("Recommendation", e.getMessage()));
        }
    }

    @Override
    @Transactional
    public Result<Recommendation, ApplicationError> handle(ImplementRecommendationCommand command) {
        try {
            var recommendationOpt = recommendationRepository.findById(command.recommendationId());
            if (recommendationOpt.isEmpty())
                return Result.failure(ApplicationError.notFound(
                        "Recommendation", command.recommendationId().getValue().toString()));
            var recommendation = recommendationOpt.get();
            recommendation.implement(command);
            var saved = recommendationRepository.save(recommendation);
            log.info("Recommendation implemented: id={}", command.recommendationId().getValue());
            return Result.success(saved);
        } catch (IllegalArgumentException e) {
            return Result.failure(ApplicationError.businessRuleViolation("Recommendation", e.getMessage()));
        }
    }
}