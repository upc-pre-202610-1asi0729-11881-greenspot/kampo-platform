package com.acme.kampo.platform.report.domain.model.commands;

import com.acme.kampo.platform.report.domain.model.enums.ReportType;
import com.acme.kampo.platform.report.domain.model.valueobjects.FileUrl;
import com.acme.kampo.platform.report.domain.model.valueobjects.SeasonId;
import com.acme.kampo.platform.report.domain.model.valueobjects.UserId;

public record GenerateReportCommand(
        ReportType type,
        FileUrl fileUrl,
        UserId userId,
        SeasonId seasonId
) {

    public GenerateReportCommand{
        if (type == null)
            throw new IllegalArgumentException("ReportType must not be null");
        if (userId == null)
            throw new IllegalArgumentException("UserId must not be null");
        if (seasonId == null)
            throw new IllegalArgumentException("SeasonId must not be null");
    }

}
