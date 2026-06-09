package com.acme.kampo.platform.alert.interfaces.rest;

import com.acme.kampo.platform.alert.application.commandservices.AlertRuleCommandService;
import com.acme.kampo.platform.alert.application.queryservices.AlertRuleQueryService;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.EvaluateConditionCommand;
import com.acme.kampo.platform.alert.domain.model.queries.GetAlertRuleByIdQuery;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertRulesQuery;
import com.acme.kampo.platform.alert.interfaces.rest.resources.AlertRuleResource;
import com.acme.kampo.platform.alert.interfaces.rest.resources.CreateAlertRuleResource;
import com.acme.kampo.platform.alert.interfaces.rest.transform.AlertRuleResourceAssembler;
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

/**
 * Inbound service in the interface layer for AlertRule management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/alert-rules", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Alert Rules", description = "Endpoints for alert rule management")
public class AlertRuleController {

    private final AlertRuleCommandService alertRuleCommandService;
    private final AlertRuleQueryService alertRuleQueryService;

    public AlertRuleController(AlertRuleCommandService alertRuleCommandService,
                               AlertRuleQueryService alertRuleQueryService) {
        this.alertRuleCommandService = alertRuleCommandService;
        this.alertRuleQueryService = alertRuleQueryService;
    }

    @Operation(summary = "Configure an alert rule",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(schema = @Schema(implementation = CreateAlertRuleResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alert rule configured",
                    content = @Content(schema = @Schema(implementation = AlertRuleResource.class))),
            @ApiResponse(responseCode = "409", description = "Conflict — alert rule already exists for this field and reading type",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> configureAlertRule(@Valid @RequestBody CreateAlertRuleResource resource) {
        log.debug("POST /api/v1/alert-rules - fieldId={}, readingType={}",
                resource.fieldId(), resource.readingType());
        var command = AlertRuleResourceAssembler.toCommand(resource);
        var result = alertRuleCommandService.handle(command);
        return switch (result) {
            case Result.Success<AlertRule, ApplicationError> s -> {
                var response = AlertRuleResourceAssembler.toResource(s.value());
                log.debug("POST /api/v1/alert-rules - created id={}", response.id());
                yield ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            case Result.Failure<AlertRule, ApplicationError> f -> {
                log.warn("POST /api/v1/alert-rules - failed: {}", f.error());
                var problem = ProblemDetail.forStatus(HttpStatus.CONFLICT);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(HttpStatus.CONFLICT).body(problem);
            }
        };
    }

    @Operation(summary = "Get an alert rule by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert rule found",
                    content = @Content(schema = @Schema(implementation = AlertRuleResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert rule not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{alertRuleId}")
    public ResponseEntity<AlertRuleResource> getAlertRuleById(
            @Parameter(name = "alertRuleId", description = "Alert rule ID", required = true)
            @PathVariable Long alertRuleId) {
        log.debug("GET /api/v1/alert-rules/{}", alertRuleId);
        var result = alertRuleQueryService.handle(new GetAlertRuleByIdQuery(alertRuleId));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AlertRuleResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all alert rules")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert rules retrieved",
                    content = @Content(schema = @Schema(implementation = AlertRuleResource[].class)))
    })
    @GetMapping
    public ResponseEntity<List<AlertRuleResource>> getAllAlertRules() {
        log.debug("GET /api/v1/alert-rules");
        var result = alertRuleQueryService.handle(new GetAllAlertRulesQuery()).stream()
                .map(AlertRuleResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Evaluate condition of an alert rule")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Condition evaluated",
                    content = @Content(schema = @Schema(implementation = Boolean.class))),
            @ApiResponse(responseCode = "404", description = "Alert rule not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping("/{alertRuleId}/evaluate")
    public ResponseEntity<?> evaluateCondition(
            @Parameter(name = "alertRuleId", description = "Alert rule ID", required = true)
            @PathVariable Long alertRuleId) {
        log.debug("POST /api/v1/alert-rules/{}/evaluate", alertRuleId);
        var result = alertRuleCommandService.handle(new EvaluateConditionCommand(alertRuleId));
        return switch (result) {
            case Result.Success<Boolean, ApplicationError> s -> {
                log.debug("POST /api/v1/alert-rules/{}/evaluate - result={}", alertRuleId, s.value());
                yield ResponseEntity.ok(s.value());
            }
            case Result.Failure<Boolean, ApplicationError> f -> {
                log.warn("POST /api/v1/alert-rules/{}/evaluate - failed: {}", alertRuleId, f.error());
                var status = f.error().code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                var problem = ProblemDetail.forStatus(status);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(status).body(problem);
            }
        };
    }
}