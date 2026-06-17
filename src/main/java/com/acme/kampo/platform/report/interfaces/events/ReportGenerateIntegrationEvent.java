package com.acme.kampo.platform.report.interfaces.events;

import com.acme.kampo.platform.report.domain.model.enums.ReportType;

public record ReportGenerateIntegrationEvent(
        Long reportId,
        ReportType type,
        Long userId,
        Long seasonId
) {
}
