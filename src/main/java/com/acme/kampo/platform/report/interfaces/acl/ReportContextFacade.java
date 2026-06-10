package com.acme.kampo.platform.report.interfaces.acl;

public interface ReportContextFacade {

    Long createReport(Long userId, Long seasonId);
    Long fetchReportById(Long id);

}
