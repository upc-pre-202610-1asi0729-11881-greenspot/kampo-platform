package com.acme.kampo.platform.report.domain.model.queries;

import com.acme.kampo.platform.report.domain.model.valueObjects.ReportId;

public record GetReportByIdQuery(ReportId reportId) {

    public GetReportByIdQuery{
        if(reportId == null)
            throw new IllegalArgumentException("ReportId must not be null");
    }

}
