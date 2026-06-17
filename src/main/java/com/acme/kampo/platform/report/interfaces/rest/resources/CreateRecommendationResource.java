package com.acme.kampo.platform.report.interfaces.rest.resources;
import com.acme.kampo.platform.report.domain.model.enums.PriorityLevel;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request resource to generate a recommendation")
public record CreateRecommendationResource(

        @Schema(description = "Priority level", example = "HIGH")
        @NotNull(message = "priority must not be null")
        PriorityLevel priority,

        @Schema(description = "Report ID", example = "1")
        @NotNull(message = "reportId must not be null")
        Long reportId) {
}
