package com.acme.kampo.platform.report.domain.model.commands;

import com.acme.kampo.platform.report.domain.model.enums.PriorityLevel;
import com.acme.kampo.platform.report.domain.model.valueObjects.ReportId;

public record GenerateRecommendationCommand(
        PriorityLevel priority,
        ReportId reportId) {

    public GenerateRecommendationCommand{
        if(priority == null)
            throw new IllegalArgumentException("Priority must not be null");
        if(reportId == null)
            throw new IllegalArgumentException("ReportId must not be null");
    }

}
