package com.acme.kampo.platform.report.application.acl;

import com.acme.kampo.platform.report.application.commandservices.ReportCommandService;
import com.acme.kampo.platform.report.application.queryservices.ReportQueryService;
import com.acme.kampo.platform.report.domain.model.queries.GetAllReportsQuery;
import com.acme.kampo.platform.report.domain.model.queries.GetReportByIdQuery;
import com.acme.kampo.platform.report.domain.model.valueobjects.ReportId;
import com.acme.kampo.platform.report.interfaces.acl.ReportContextFacade;
import org.springframework.stereotype.Service;

@Service
public class ReportContextFacadeImpl implements ReportContextFacade {

    private final ReportCommandService reportCommandService;
    private final ReportQueryService reportQueryService;

    public ReportContextFacadeImpl(ReportCommandService reportCommandService,
                                   ReportQueryService reportQueryService) {
        this.reportCommandService = reportCommandService;
        this.reportQueryService = reportQueryService;
    }

    @Override
    public Long createReport(Long userId, Long seasonId) {
        return reportQueryService.handle(new GetAllReportsQuery())
                .stream()
                .findFirst()
                .map(report -> report.getId().getValue())
                .orElse(null);
    }

    @Override
    public Long fetchReportById(Long id) {
        return reportQueryService.handle(new GetReportByIdQuery(ReportId.of(id)))
                .map(report -> report.getId().getValue())
                .orElse(null);
    }
}