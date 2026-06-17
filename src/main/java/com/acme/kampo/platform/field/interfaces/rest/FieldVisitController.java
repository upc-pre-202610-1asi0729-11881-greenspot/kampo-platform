package com.acme.kampo.platform.field.interfaces.rest;

import com.acme.kampo.platform.field.application.commandservices.FieldVisitCommandService;
import com.acme.kampo.platform.field.application.queryservices.FieldVisitQueryService;
import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.commands.CompleteFieldVisitCommand;
import com.acme.kampo.platform.field.domain.model.queries.GetFieldVisitByIdQuery;
import com.acme.kampo.platform.field.domain.model.queries.GetFieldVisitsByFieldQuery;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.interfaces.rest.resources.CreateFieldVisitResource;
import com.acme.kampo.platform.field.interfaces.rest.resources.FieldVisitResource;
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
 * Inbound service in the interface layer for FieldVisit management.
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/v1/field-visits", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Field Visits", description = "Endpoints for scheduling and completing field visits")
public class FieldVisitController {

    private final FieldVisitCommandService fieldVisitCommandService;
    private final FieldVisitQueryService   fieldVisitQueryService;

    public FieldVisitController(FieldVisitCommandService fieldVisitCommandService,
                                FieldVisitQueryService fieldVisitQueryService) {
        this.fieldVisitCommandService = fieldVisitCommandService;
        this.fieldVisitQueryService   = fieldVisitQueryService;
    }

    @Operation(summary = "Schedule a field visit")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Field visit scheduled",
                    content = @Content(schema = @Schema(implementation = FieldVisitResource.class))),
            @ApiResponse(responseCode = "400", description = "Invalid payload",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> scheduleFieldVisit(@Valid @RequestBody CreateFieldVisitResource resource) {
        log.debug("POST /api/v1/field-visits - fieldId={}", resource.fieldId());
        var result = fieldVisitCommandService.handle(
                FieldOperationResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<FieldVisit, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(FieldOperationResourceAssembler.toResource(s.value()));
            case Result.Failure<FieldVisit, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Complete a field visit",
            description = "Marks a scheduled visit as DONE and publishes FieldVisitCompletedEvent")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Field visit completed",
                    content = @Content(schema = @Schema(implementation = FieldVisitResource.class))),
            @ApiResponse(responseCode = "404", description = "Field visit not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Field visit already completed",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PatchMapping("/{fieldVisitId}/complete")
    public ResponseEntity<?> completeFieldVisit(
            @Parameter(description = "Field visit ID", required = true)
            @PathVariable Long fieldVisitId) {
        log.debug("PATCH /api/v1/field-visits/{}/complete", fieldVisitId);
        var result = fieldVisitCommandService.handle(new CompleteFieldVisitCommand(fieldVisitId));
        return switch (result) {
            case Result.Success<FieldVisit, ApplicationError> s ->
                    ResponseEntity.ok(FieldOperationResourceAssembler.toResource(s.value()));
            case Result.Failure<FieldVisit, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get a field visit by ID")
    @GetMapping("/{fieldVisitId}")
    public ResponseEntity<FieldVisitResource> getFieldVisitById(
            @Parameter(description = "Field visit ID", required = true)
            @PathVariable Long fieldVisitId) {
        log.debug("GET /api/v1/field-visits/{}", fieldVisitId);
        var result = fieldVisitQueryService.handle(
                new GetFieldVisitByIdQuery(FieldVisitId.of(fieldVisitId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(FieldOperationResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all field visits for a field")
    @GetMapping
    public ResponseEntity<List<FieldVisitResource>> getFieldVisitsByField(
            @Parameter(description = "Field ID", required = true)
            @RequestParam Long fieldId) {
        log.debug("GET /api/v1/field-visits?fieldId={}", fieldId);
        var result = fieldVisitQueryService.handle(
                        new GetFieldVisitsByFieldQuery(FieldId.of(fieldId)))
                .stream().map(FieldOperationResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    private ResponseEntity<ProblemDetail> toErrorResponse(ApplicationError error) {
        var status = error.code().endsWith("_NOT_FOUND")       ? HttpStatus.NOT_FOUND
                : error.code().equals("BUSINESS_RULE_VIOLATION") ? HttpStatus.CONFLICT
                  : HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);
        problem.setTitle(error.code());
        problem.setDetail(error.message());
        return ResponseEntity.status(status).body(problem);
    }
}