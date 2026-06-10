package com.acme.kampo.platform.report.application.commandservices;

import com.acme.kampo.platform.report.domain.model.aggregates.Recommendation;
import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.domain.model.commands.GenerateRecommendationCommand;
import com.acme.kampo.platform.report.domain.model.commands.GenerateReportCommand;
import com.acme.kampo.platform.report.domain.model.commands.ImplementRecommendationCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

public interface ReportCommandService {

    Result<Report, ApplicationError> handle(GenerateReportCommand command);
    Result<Recommendation, ApplicationError> handle(GenerateRecommendationCommand command);
    Result<Recommendation, ApplicationError> handle(ImplementRecommendationCommand command);
}
