package com.acme.kampo.platform.report.interfaces.rest.resources;

import com.acme.kampo.platform.report.domain.model.enums.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request resource to generate a report")
public record CreateReportResource(

        @Schema(description = "Report type", example = "FINANCIAL")
        @NotNull(message = "type must not be null")
        ReportType type,

        @Schema(description = "File URL", example = "https://storage.com/report.pdf")
        String fileUrl,

        @Schema(description = "User ID", example = "1")
        @NotNull(message = "userId must not be null")
        Long userId,

        @Schema(description = "Season ID", example = "1")
        @NotNull(message = "seasonId must not be null")
        Long seasonId) {
}
