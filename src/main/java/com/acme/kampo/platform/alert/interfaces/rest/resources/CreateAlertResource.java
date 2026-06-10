package com.acme.kampo.platform.alert.interfaces.rest.resources;

import com.acme.kampo.platform.alert.domain.model.enums.AlertPriority;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for sending a new alert.
 * Received as JSON body in POST /api/v1/alerts.
 */
@Schema(description = "Resource to send a new alert triggered by a rule violation")
public record CreateAlertResource(

        @Schema(description = "Human-readable description of what occurred", example = "Temperatura crítica detectada: 38°C supera el umbral de 35°C")
        String message,

        @Schema(description = "Urgency level of the alert", example = "HIGH")
        AlertPriority priority,

        @Schema(description = "ID of the field where the condition was detected", example = "1")
        Long fieldId,

        @Schema(description = "ID of the alert rule that triggered this alert", example = "1")
        Long alertRuleId
) {}