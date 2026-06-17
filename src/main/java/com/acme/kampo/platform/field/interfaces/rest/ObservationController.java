package com.acme.kampo.platform.field.interfaces.rest;

import com.acme.kampo.platform.field.application.commandservices.ObservationCommandService;
import com.acme.kampo.platform.field.application.queryservices.ObservationQueryService;
import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.queries.GetObservationByIdQuery;
import com.acme.kampo.platform.field.domain.model.queries.GetObservationsByFieldVisitQuery;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.domain.model.valueobjects.ObservationId;
import com.acme.kampo.platform.field.interfaces.rest.resources.CreateObservationResource;
import com.acme.kampo.platform.field.interfaces.rest.resources.ObservationResource;
import com.acme.kampo.platform.field.interfaces.rest.transform.FieldOperationResourceAssembler;
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
 * Inbound service in the interface layer for Observation management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/observations", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Observations", description = "Endpoints for registering and querying field observations")
public class ObservationController {

    private final ObservationCommandService observationCommandService;
    private final ObservationQueryService   observationQueryService;

    public ObservationController(ObservationCommandService observationCommandService,
                                 ObservationQueryService observationQueryService) {
        this.observationCommandService = observationCommandService;
        this.observationQueryService   = observationQueryService;
    }

    @Operation(summary = "Register an observation",
            description = "Records pest and disease findings during a field visit. Publishes ObservationRegisteredEvent.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Observation registered",
                    content = @Content(schema = @Schema(implementation = ObservationResource.class))),
            @ApiResponse(responseCode = "404", description = "Field visit not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerObservation(
            @Valid @RequestBody CreateObservationResource resource) {
        log.debug("POST /api/v1/observations - fieldVisitId={}", resource.fieldVisitId());
        var result = observationCommandService.handle(
                FieldOperationResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Observation, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(FieldOperationResourceAssembler.toResource(s.value()));
            case Result.Failure<Observation, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get an observation by ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Observation found",
                    content = @Content(schema = @Schema(implementation = ObservationResource.class))),
            @ApiResponse(responseCode = "404", description = "Observation not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @GetMapping("/{observationId}")
    public ResponseEntity<ObservationResource> getObservationById(
            @Parameter(description = "Observation ID", required = true)
            @PathVariable Long observationId) {
        log.debug("GET /api/v1/observations/{}", observationId);
        var result = observationQueryService.handle(
                new GetObservationByIdQuery(ObservationId.of(observationId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(FieldOperationResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all observations for a field visit")
    @GetMapping
    public ResponseEntity<List<ObservationResource>> getObservationsByFieldVisit(
            @Parameter(description = "Field visit ID", required = true)
            @RequestParam Long fieldVisitId) {
        log.debug("GET /api/v1/observations?fieldVisitId={}", fieldVisitId);
        var result = observationQueryService.handle(
                        new GetObservationsByFieldVisitQuery(FieldVisitId.of(fieldVisitId)))
                .stream().map(FieldOperationResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<ProblemDetail> toErrorResponse(ApplicationError error) {
        var status = error.code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND
                : HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);
        problem.setTitle(error.code());
        problem.setDetail(error.message());
        return ResponseEntity.status(status).body(problem);
    }
}