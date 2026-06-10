package com.acme.kampo.platform.report.interfaces.rest.resources;
import com.acme.kampo.platform.report.domain.model.enums.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Response resource representing a report")
public record ReportResource(

        @Schema(description = "Report ID", example = "1")
        Long id,

        @Schema(description = "Report type")
        ReportType type,

        @Schema(description = "File URL")
        String fileUrl,

        @Schema(description = "User ID")
        Long userId,

        @Schema(description = "Season ID")
        Long seasonId) {
}
