package com.acme.kampo.platform.alert.interfaces.rest.resources;

import com.acme.kampo.platform.alert.domain.model.enums.AlertPriority;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing an Alert in API responses.
 */
@Schema(description = "Alert representation")
public record AlertResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Alert message", example = "Temperature exceeded threshold")
        String message,

        @Schema(description = "Alert priority", example = "HIGH")
        AlertPriority priority,

        @Schema(description = "Whether the alert has been read", example = "false")
        boolean isRead,

        @Schema(description = "ID of the field that triggered the alert", example = "1")
        Long fieldId,

        @Schema(description = "ID of the alert rule that triggered the alert", example = "1")
        Long alertRuleId
) {}