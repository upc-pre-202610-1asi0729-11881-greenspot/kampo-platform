package com.acme.kampo.platform.alert.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for evaluating whether a sensor reading violates an alert rule.
 * Received as JSON body in POST /api/v1/alert-rules/{id}/evaluate.
 */
@Schema(description = "Resource to evaluate a sensor reading against an alert rule")
public record EvaluateAlertRuleResource(

        @Schema(description = "Current sensor reading value to evaluate", example = "38.5")
        Double currentValue
) {}