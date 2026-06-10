package com.acme.kampo.platform.report.domain.model.events;

import com.acme.kampo.platform.report.domain.model.aggregates.Report;

public record ReportGeneratedEvent(Report report) {

    public Long getReportId(){
        return report.getId() !=null ? report.getId().getValue() : null;
    }

}
