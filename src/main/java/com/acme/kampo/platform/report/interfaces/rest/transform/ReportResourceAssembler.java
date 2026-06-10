package com.acme.kampo.platform.report.interfaces.rest.transform;

import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.interfaces.rest.resources.ReportResource;

public class ReportResourceAssembler {

    public static ReportResource toResource(Report report) {
        return new ReportResource(
                report.getId().getValue(),
                report.getType(),
                report.getFileUrl() != null ? report.getFileUrl().getValue() : null,
                report.getUserId().getValue(),
                report.getSeasonId().getValue());
    }
}