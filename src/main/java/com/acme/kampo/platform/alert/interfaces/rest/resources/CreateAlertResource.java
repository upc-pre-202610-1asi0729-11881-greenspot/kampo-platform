package com.acme.kampo.platform.alert.interfaces.rest.resources;

import com.acme.kampo.platform.alert.domain.model.enums.AlertPriority;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for sending a new Alert.
 */
@Schema(description = "Resource to send a new alert")
public record CreateAlertResource(

        @Schema(description = "Alert message", example = "Temperature exceeded threshold")
        String message,

        @Schema(description = "Alert priority", example = "HIGH")
        AlertPriority priority,

        @Schema(description = "ID of the field that triggered the alert", example = "1")
        Long fieldId,

        @Schema(description = "ID of the alert rule that triggered the alert", example = "1")
        Long alertRuleId
) {}