package com.acme.kampo.platform.field.interfaces.rest.resources;

import com.acme.kampo.platform.field.domain.model.enums.Severity;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for registering a new observation during a field visit.
 */
@Schema(description = "Resource to register a new observation")
public record CreateObservationResource(

        @Schema(description = "ID of the field visit this observation belongs to", example = "1")
        Long fieldVisitId,

        @Schema(description = "General notes and findings", example = "Se observó presencia moderada de pulgones en hojas superiores")
        String notes,

        @Schema(description = "Name of the pest detected (optional)", example = "Pulgón verde")
        String pestName,

        @Schema(description = "Severity of the pest (optional)", example = "HIGH")
        Severity pestSeverity,

        @Schema(description = "Name of the disease detected (optional)", example = "Mildiu")
        String diseaseName,

        @Schema(description = "Severity of the disease (optional)", example = "MEDIUM")
        Severity diseaseSeverity,

        @Schema(description = "Agronomist recommendation for treatment",
                example = "Aplicar insecticida sistémico en las próximas 48 horas")
        String recommendation,

        @Schema(description = "URL of photo or document evidence (optional)",
                example = "https://storage.acme.io/evidence/obs-001.jpg")
        String evidenceUrl
) {}