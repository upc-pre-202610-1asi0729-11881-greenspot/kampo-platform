package com.acme.kampo.platform.alert.interfaces.rest.resources;

import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for configuring a new alert rule.
 * Received as JSON body in POST /api/v1/alert-rules.
 */
@Schema(description = "Resource to configure a new alert rule for a field")
public record CreateAlertRuleResource(

        @Schema(description = "Type of sensor reading to monitor", example = "TEMPERATURE")
        ReadingType readingType,

        @Schema(description = "Comparison operator applied against the threshold", example = "GREATER_THAN")
        ConditionOperator conditionOperator,

        @Schema(description = "Threshold value that triggers the alert", example = "35.0")
        Double threshold,

        @Schema(description = "Severity of the condition if triggered", example = "HIGH")
        SeverityLevel severity,

        @Schema(description = "ID of the field this rule monitors", example = "1")
        Long fieldId
) {}