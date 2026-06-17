package com.acme.kampo.platform.report.interfaces.rest.resources;

import com.acme.kampo.platform.report.domain.model.enums.PriorityLevel;
import com.acme.kampo.platform.report.domain.model.enums.RecommendationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Response resource representing a recommendation")
public record RecommendationResource(

        @Schema(description = "Recommendation ID")
        Long id,

        @Schema(description = "Priority level")
        PriorityLevel priority,

        @Schema(description = "Status")
        RecommendationStatus status,

        @Schema(description = "Created at")
        LocalDateTime createdAt,

        @Schema(description = "Implemented at")
        LocalDateTime implementedAt,

        @Schema(description = "Report ID")
        Long reportId) {
}
