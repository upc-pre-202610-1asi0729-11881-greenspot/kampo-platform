package com.acme.kampo.platform.field.domain.model.commands;

import com.acme.kampo.platform.field.domain.model.enums.Severity;

/**
 * Command to register a new observation during a field visit.
 *
 * <p>All pest and disease fields are optional — a visit may reveal only one
 * type of issue, or none at all (in which case notes captures the finding).</p>
 *
 * @param fieldVisitId      ID of the completed or in-progress field visit
 * @param notes             general observations and findings
 * @param pestName          name of the pest detected (nullable)
 * @param pestSeverity      severity of the pest (nullable)
 * @param diseaseName       name of the disease detected (nullable)
 * @param diseaseSeverity   severity of the disease (nullable)
 * @param recommendation    agronomist recommendation for treatment
 * @param evidenceUrl       URL of photo or document evidence (nullable)
 */
public record RegisterObservationCommand(
        Long fieldVisitId,
        String notes,
        String pestName,
        Severity pestSeverity,
        String diseaseName,
        Severity diseaseSeverity,
        String recommendation,
        String evidenceUrl
) {
    public RegisterObservationCommand {
        if (fieldVisitId == null || fieldVisitId <= 0)
            throw new IllegalArgumentException("fieldVisitId must be positive");
        if (notes == null || notes.isBlank())
            throw new IllegalArgumentException("notes must not be blank");
        if (recommendation == null || recommendation.isBlank())
            throw new IllegalArgumentException("recommendation must not be blank");
    }
}