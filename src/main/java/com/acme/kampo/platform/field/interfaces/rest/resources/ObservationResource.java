package com.acme.kampo.platform.field.interfaces.rest.resources;

import com.acme.kampo.platform.field.domain.model.enums.Severity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing an Observation in API responses.
 */
@Schema(description = "Observation representation")
public record ObservationResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "ID of the associated field visit", example = "1")
        Long fieldVisitId,

        @Schema(description = "General notes and findings")
        String notes,

        @Schema(description = "Name of the pest detected (nullable)")
        String pestName,

        @Schema(description = "Severity of the pest (nullable)")
        Severity pestSeverity,

        @Schema(description = "Name of the disease detected (nullable)")
        String diseaseName,

        @Schema(description = "Severity of the disease (nullable)")
        Severity diseaseSeverity,

        @Schema(description = "Agronomist recommendation")
        String recommendation,

        @Schema(description = "URL of photo or document evidence (nullable)")
        String evidenceUrl
) {}