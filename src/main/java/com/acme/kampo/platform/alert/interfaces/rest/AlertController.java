package com.acme.kampo.platform.alert.interfaces.rest;

import com.acme.kampo.platform.alert.application.commandservices.AlertCommandService;
import com.acme.kampo.platform.alert.application.queryservices.AlertQueryService;
import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.commands.MarkAlertReadCommand;
import com.acme.kampo.platform.alert.domain.model.queries.GetAlertByIdQuery;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertsQuery;
import com.acme.kampo.platform.alert.interfaces.rest.resources.AlertResource;
import com.acme.kampo.platform.alert.interfaces.rest.resources.CreateAlertResource;
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

/**
 * Inbound service in the interface layer for Alert management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/alerts", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Alerts", description = "Endpoints for alert management")
public class AlertController {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public AlertController(AlertCommandService alertCommandService,
                           AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @Operation(summary = "Send a new alert",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(schema = @Schema(implementation = CreateAlertResource.class))))
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Alert sent",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert rule not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "400", description = "Bad request — invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> sendAlert(@Valid @RequestBody CreateAlertResource resource) {
        log.debug("POST /api/v1/alerts - fieldId={}, alertRuleId={}",
                resource.fieldId(), resource.alertRuleId());
        var command = AlertResourceAssembler.toCommand(resource);
        var result = alertCommandService.handle(command);
        return switch (result) {
            case Result.Success<Alert, ApplicationError> s -> {
                var response = AlertResourceAssembler.toResource(s.value());
                log.debug("POST /api/v1/alerts - created id={}", response.id());
                yield ResponseEntity.status(HttpStatus.CREATED).body(response);
            }
            case Result.Failure<Alert, ApplicationError> f -> {
                log.warn("POST /api/v1/alerts - failed: {}", f.error());
                var status = f.error().code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.BAD_REQUEST;
                var problem = ProblemDetail.forStatus(status);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(status).body(problem);
            }
        };
    }

    @Operation(summary = "Get an alert by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert found",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{alertId}")
    public ResponseEntity<AlertResource> getAlertById(
            @Parameter(name = "alertId", description = "Alert ID", required = true)
            @PathVariable Long alertId) {
        log.debug("GET /api/v1/alerts/{}", alertId);
        var result = alertQueryService.handle(new GetAlertByIdQuery(alertId));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(AlertResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all alerts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alerts retrieved",
                    content = @Content(schema = @Schema(implementation = AlertResource[].class)))
    })
    @GetMapping
    public ResponseEntity<List<AlertResource>> getAllAlerts() {
        log.debug("GET /api/v1/alerts");
        var result = alertQueryService.handle(new GetAllAlertsQuery()).stream()
                .map(AlertResourceAssembler::toResource)
                .toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Mark an alert as read")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Alert marked as read",
                    content = @Content(schema = @Schema(implementation = AlertResource.class))),
            @ApiResponse(responseCode = "404", description = "Alert not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Alert already read",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{alertId}/mark-read")
    public ResponseEntity<?> markAlertRead(
            @Parameter(name = "alertId", description = "Alert ID", required = true)
            @PathVariable Long alertId) {
        log.debug("PATCH /api/v1/alerts/{}/mark-read", alertId);
        var result = alertCommandService.handle(new MarkAlertReadCommand(alertId));
        return switch (result) {
            case Result.Success<Alert, ApplicationError> s -> {
                var response = AlertResourceAssembler.toResource(s.value());
                log.debug("PATCH /api/v1/alerts/{}/mark-read - isRead={}", alertId, response.isRead());
                yield ResponseEntity.ok(response);
            }
            case Result.Failure<Alert, ApplicationError> f -> {
                log.warn("PATCH /api/v1/alerts/{}/mark-read - failed: {}", alertId, f.error());
                var status = f.error().code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND : HttpStatus.CONFLICT;
                var problem = ProblemDetail.forStatus(status);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(status).body(problem);
            }
        };
    }
}