package com.acme.kampo.platform.organization.interfaces.rest;

import com.acme.kampo.platform.organization.application.commandservices.OrganizationCommandService;
import com.acme.kampo.platform.organization.application.queryservices.OrganizationQueryService;
import com.acme.kampo.platform.organization.domain.model.aggregates.*;
import com.acme.kampo.platform.organization.domain.model.commands.DeleteOrganizationCommand;
import com.acme.kampo.platform.organization.domain.model.queries.*;
import com.acme.kampo.platform.organization.domain.model.valueobjects.*;
import com.acme.kampo.platform.organization.interfaces.rest.resources.*;
import com.acme.kampo.platform.organization.interfaces.rest.transform.OrganizationResourceAssembler;
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
 * Inbound service in the interface layer for the Organization bounded context.
 * Handles Organization, Fundo, Field and Crop endpoints.
 */
@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Organization", description = "Endpoints for organization, fundo, field and crop management")
public class OrganizationController {

    private final OrganizationCommandService commandService;
    private final OrganizationQueryService   queryService;

    public OrganizationController(OrganizationCommandService commandService,
                                  OrganizationQueryService queryService) {
        this.commandService = commandService;
        this.queryService   = queryService;
    }

    // ── Organizations ─────────────────────────────────────────────────────────

    @Operation(summary = "Create an organization")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Organization created",
                    content = @Content(schema = @Schema(implementation = OrganizationResource.class))),
            @ApiResponse(responseCode = "409", description = "Name already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/organizations", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createOrganization(@Valid @RequestBody CreateOrganizationResource resource) {
        log.debug("POST /api/v1/organizations - name={}", resource.name());
        var result = commandService.handle(OrganizationResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Organization, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(OrganizationResourceAssembler.toResource(s.value()));
            case Result.Failure<Organization, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Update an organization")
    @PutMapping(value = "/api/v1/organizations/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updateOrganization(
            @Parameter(description = "Organization ID", required = true) @PathVariable Long id,
            @Valid @RequestBody UpdateOrganizationResource resource) {
        log.debug("PUT /api/v1/organizations/{}", id);
        var result = commandService.handle(OrganizationResourceAssembler.toCommand(id, resource));
        return switch (result) {
            case Result.Success<Organization, ApplicationError> s ->
                    ResponseEntity.ok(OrganizationResourceAssembler.toResource(s.value()));
            case Result.Failure<Organization, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Delete an organization")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Organization deleted"),
            @ApiResponse(responseCode = "404", description = "Organization not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @DeleteMapping("/api/v1/organizations/{id}")
    public ResponseEntity<?> deleteOrganization(
            @Parameter(description = "Organization ID", required = true) @PathVariable Long id) {
        log.debug("DELETE /api/v1/organizations/{}", id);
        var result = commandService.handle(new DeleteOrganizationCommand(id));
        return switch (result) {
            case Result.Success<Void, ApplicationError> s -> ResponseEntity.noContent().build();
            case Result.Failure<Void, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get an organization by ID")
    @GetMapping("/api/v1/organizations/{id}")
    public ResponseEntity<OrganizationResource> getOrganizationById(
            @Parameter(description = "Organization ID", required = true) @PathVariable Long id) {
        log.debug("GET /api/v1/organizations/{}", id);
        var result = queryService.handle(new GetOrganizationByIdQuery(OrganizationId.of(id)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(OrganizationResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all organizations")
    @GetMapping("/api/v1/organizations")
    public ResponseEntity<List<OrganizationResource>> getAllOrganizations() {
        log.debug("GET /api/v1/organizations");
        var result = queryService.handle(new GetAllOrganizationsQuery())
                .stream().map(OrganizationResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    // ── Fundos ────────────────────────────────────────────────────────────────

    @Operation(summary = "Register a fundo under an organization")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Fundo registered",
                    content = @Content(schema = @Schema(implementation = FundoResource.class))),
            @ApiResponse(responseCode = "404", description = "Organization not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/fundos", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerFundo(@Valid @RequestBody RegisterFundoResource resource) {
        log.debug("POST /api/v1/fundos - organizationId={}", resource.organizationId());
        var result = commandService.handle(OrganizationResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Fundo, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(OrganizationResourceAssembler.toResource(s.value()));
            case Result.Failure<Fundo, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get a fundo by ID")
    @GetMapping("/api/v1/fundos/{id}")
    public ResponseEntity<FundoResource> getFundoById(
            @Parameter(description = "Fundo ID", required = true) @PathVariable Long id) {
        log.debug("GET /api/v1/fundos/{}", id);
        var result = queryService.handle(new GetFundoByIdQuery(FundoId.of(id)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(OrganizationResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all fundos for an organization")
    @GetMapping("/api/v1/organizations/{organizationId}/fundos")
    public ResponseEntity<List<FundoResource>> getFundosByOrganization(
            @Parameter(description = "Organization ID", required = true) @PathVariable Long organizationId) {
        log.debug("GET /api/v1/organizations/{}/fundos", organizationId);
        var result = queryService.handle(
                        new GetFundosByOrganizationQuery(OrganizationId.of(organizationId)))
                .stream().map(OrganizationResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    // ── Fields ────────────────────────────────────────────────────────────────

    @Operation(summary = "Register a field within a fundo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Field registered",
                    content = @Content(schema = @Schema(implementation = FieldResource.class))),
            @ApiResponse(responseCode = "404", description = "Fundo not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/fields", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerField(@Valid @RequestBody RegisterFieldResource resource) {
        log.debug("POST /api/v1/fields - fundoId={}", resource.fundoId());
        var result = commandService.handle(OrganizationResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Field, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(OrganizationResourceAssembler.toResource(s.value()));
            case Result.Failure<Field, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get a field by ID")
    @GetMapping("/api/v1/fields/{id}")
    public ResponseEntity<FieldResource> getFieldById(
            @Parameter(description = "Field ID", required = true) @PathVariable Long id) {
        log.debug("GET /api/v1/fields/{}", id);
        var result = queryService.handle(new GetFieldByIdQuery(FieldId.of(id)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(OrganizationResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all fields for a fundo")
    @GetMapping("/api/v1/fundos/{fundoId}/fields")
    public ResponseEntity<List<FieldResource>> getFieldsByFundo(
            @Parameter(description = "Fundo ID", required = true) @PathVariable Long fundoId) {
        log.debug("GET /api/v1/fundos/{}/fields", fundoId);
        var result = queryService.handle(new GetFieldsByFundoQuery(FundoId.of(fundoId)))
                .stream().map(OrganizationResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    // ── Crops ─────────────────────────────────────────────────────────────────

    @Operation(summary = "Register a crop within a field")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Crop registered",
                    content = @Content(schema = @Schema(implementation = CropResource.class))),
            @ApiResponse(responseCode = "404", description = "Field not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/crops", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerCrop(@Valid @RequestBody RegisterCropResource resource) {
        log.debug("POST /api/v1/crops - fieldId={}", resource.fieldId());
        var result = commandService.handle(OrganizationResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Crop, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(OrganizationResourceAssembler.toResource(s.value()));
            case Result.Failure<Crop, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get a crop by ID")
    @GetMapping("/api/v1/crops/{id}")
    public ResponseEntity<CropResource> getCropById(
            @Parameter(description = "Crop ID", required = true) @PathVariable Long id) {
        log.debug("GET /api/v1/crops/{}", id);
        var result = queryService.handle(new GetCropByIdQuery(CropId.of(id)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(OrganizationResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all crops for a field")
    @GetMapping("/api/v1/fields/{fieldId}/crops")
    public ResponseEntity<List<CropResource>> getCropsByField(
            @Parameter(description = "Field ID", required = true) @PathVariable Long fieldId) {
        log.debug("GET /api/v1/fields/{}/crops", fieldId);
        var result = queryService.handle(new GetCropsByFieldQuery(FieldId.of(fieldId)))
                .stream().map(OrganizationResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    // ── Error helper ──────────────────────────────────────────────────────────

    private ResponseEntity<ProblemDetail> toErrorResponse(ApplicationError error) {
        var status = error.code().endsWith("_NOT_FOUND") ? HttpStatus.NOT_FOUND
                : error.code().endsWith("_CONFLICT")    ? HttpStatus.CONFLICT
                  : HttpStatus.BAD_REQUEST;
        var problem = ProblemDetail.forStatus(status);
        problem.setTitle(error.code());
        problem.setDetail(error.message());
        return ResponseEntity.status(status).body(problem);
    }
}