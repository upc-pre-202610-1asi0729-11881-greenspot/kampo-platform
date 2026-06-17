package com.acme.kampo.platform.alert.interfaces.rest.resources;

import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing an AlertRule in API responses.
 */
@Schema(description = "Alert rule representation")
public record AlertRuleResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Type of sensor reading monitored", example = "TEMPERATURE")
        ReadingType readingType,

        @Schema(description = "Comparison operator", example = "GREATER_THAN")
        ConditionOperator conditionOperator,

        @Schema(description = "Threshold value", example = "35.0")
        Double threshold,

        @Schema(description = "Severity level", example = "HIGH")
        SeverityLevel severity,

        @Schema(description = "ID of the monitored field", example = "1")
        Long fieldId
) {}