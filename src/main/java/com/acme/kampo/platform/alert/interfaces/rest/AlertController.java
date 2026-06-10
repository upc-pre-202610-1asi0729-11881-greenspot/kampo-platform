package com.acme.kampo.platform.alert.interfaces.rest;

import com.acme.kampo.platform.alert.application.commandservices.AlertCommandService;
import com.acme.kampo.platform.alert.application.queryservices.AlertQueryService;
import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.MarkAlertReadCommand;
import com.acme.kampo.platform.alert.domain.model.queries.*;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.interfaces.rest.resources.*;
import com.acme.kampo.platform.alert.interfaces.rest.transform.AlertResourceAssembler;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Inbound service in the interface layer for the Alert bounded context.
 * Handles both {@link AlertRule} configuration and {@link Alert} management.
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Endpoints for alert rule configuration and alert management")
public class AlertController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService   alertQueryService;

    public AlertController(AlertCommandService alertCommandService,
                           AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService   = alertQueryService;
    }

    // ── Alert Rules ───────────────────────────────────────────────────────────

    @Operation(summary = "Configure an alert rule",
            description = "Creates a new alert rule that monitors a sensor reading type against a threshold for a given field")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alert rule configured",
                    content = @Content(schema = @Schema(implementation = AlertRuleResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/alert-rules", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createAlertRule(@Valid @RequestBody CreateAlertRuleResource resource) {
        log.debug("POST /api/v1/alert-rules - fieldId={}, type={}", resource.fieldId(), resource.readingType());
        var result = alertCommandService.handle(AlertResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<AlertRule, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(AlertResourceAssembler.toResource(s.value()));
            case Result.Failure<AlertRule, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get an alert rule by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alert rule found",
                    content = @Content(schema = @Schema(implementation = AlertRuleResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert rule not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/api/v1/alert-rules/{alertRuleId}")
    public ResponseEntity<AlertRuleResource> getAlertRuleById(
            @Parameter(description = "Alert rule ID", required = true) @PathVariable Long alertRuleId) {
        log.debug("GET /api/v1/alert-rules/{}", alertRuleId);
        var result = alertQueryService.handle(
                new GetAlertRuleByIdQuery(AlertRuleId.of(alertRuleId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AlertResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all alert rules")
    @GetMapping("/api/v1/alert-rules")
    public ResponseEntity<List<AlertRuleResource>> getAllAlertRules() {
        log.debug("GET /api/v1/alert-rules");
        var result = alertQueryService.handle(new GetAllAlertRulesQuery())
                .stream().map(AlertResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Evaluate a sensor reading against an alert rule",
            description = "Returns true if the current value violates the rule condition — pure read, no state mutation")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Evaluation result",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Alert rule not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/alert-rules/{alertRuleId}/evaluate",
            consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> evaluateAlertRule(
            @Parameter(description = "Alert rule ID", required = true) @PathVariable Long alertRuleId,
            @Valid @RequestBody EvaluateAlertRuleResource resource) {
        log.debug("POST /api/v1/alert-rules/{}/evaluate - value={}", alertRuleId, resource.currentValue());
        var result = alertQueryService.handle(
                AlertResourceAssembler.toQuery(alertRuleId, resource));
        return switch (result) {
            case Result.Success<Boolean, ApplicationError> s ->
                    ResponseEntity.ok(Map.of("conditionMet", s.value()));
            case Result.Failure<Boolean, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    // ── Alerts ────────────────────────────────────────────────────────────────

    @Operation(summary = "Send an alert",
            description = "Creates a new alert triggered by a rule violation. Publishes AlertSentIntegrationEvent.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Alert sent",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert rule not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/alerts", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendAlert(@Valid @RequestBody CreateAlertResource resource) {
        log.debug("POST /api/v1/alerts - fieldId={}, priority={}", resource.fieldId(), resource.priority());
        var result = alertCommandService.handle(AlertResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Alert, ApplicationError> s -> {
                log.debug("POST /api/v1/alerts - created id={}", s.value().getId().getValue());
                yield ResponseEntity.status(HttpStatus.CREATED)
                        .body(AlertResourceAssembler.toResource(s.value()));
            }
            case Result.Failure<Alert, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Mark an alert as read")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Alert marked as read",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Alert already read",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/api/v1/alerts/{alertId}/read")
    public ResponseEntity<?> markAlertRead(
            @Parameter(description = "Alert ID", required = true) @PathVariable Long alertId) {
        log.debug("PATCH /api/v1/alerts/{}/read", alertId);
        var result = alertCommandService.handle(new MarkAlertReadCommand(alertId));
        return switch (result) {
            case Result.Success<Alert, ApplicationError> s ->
                    ResponseEntity.ok(AlertResourceAssembler.toResource(s.value()));
            case Result.Failure<Alert, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get an alert by ID")
    @GetMapping("/api/v1/alerts/{alertId}")
    public ResponseEntity<AlertResource> getAlertById(
            @Parameter(description = "Alert ID", required = true) @PathVariable Long alertId) {
        log.debug("GET /api/v1/alerts/{}", alertId);
        var result = alertQueryService.handle(new GetAlertByIdQuery(AlertId.of(alertId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AlertResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all alerts")
    @GetMapping("/api/v1/alerts")
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        log.debug("GET /api/v1/alerts");
        var result = alertQueryService.handle(new GetAllAlertsQuery())
                .stream().map(AlertResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    // ── Error helper ──────────────────────────────────────────────────────────

    private ResponseEntity<ProblemDetail> toErrorResponse(ApplicationError error) {
        var status = error.code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND
                : error.code().equals("BUSINESS_RULE_VIOLATION") ? HttpStatus.CONFLICT
                  : HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);
        problem.setTitle(error.code());
        problem.setDetail(error.message());
        return ResponseEntity.status(status).body(problem);
    }
}