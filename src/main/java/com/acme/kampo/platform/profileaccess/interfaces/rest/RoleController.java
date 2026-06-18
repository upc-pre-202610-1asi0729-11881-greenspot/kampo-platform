package com.acme.kampo.platform.profileaccess.interfaces.rest;

import com.acme.kampo.platform.profileaccess.application.commandservices.RoleCommandService;
import com.acme.kampo.platform.profileaccess.application.queryservices.RoleQueryService;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.*;
import com.acme.kampo.platform.profileaccess.domain.model.queries.*;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.profileaccess.interfaces.rest.resources.*;
import com.acme.kampo.platform.profileaccess.interfaces.rest.transform.ProfileAccessResourceAssembler;
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

@Slf4j
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Roles & Permissions", description = "Endpoints for role and permission management")
public class RoleController {

    private final RoleCommandService roleCommandService;
    private final RoleQueryService   roleQueryService;

    public RoleController(RoleCommandService roleCommandService,
                          RoleQueryService roleQueryService) {
        this.roleCommandService = roleCommandService;
        this.roleQueryService   = roleQueryService;
    }

    // ── Roles ─────────────────────────────────────────────────────────────────

    @Operation(summary = "Create a role")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role created",
                    content = @Content(schema = @Schema(implementation = RoleResource.class))),
            @ApiResponse(responseCode = "409", description = "Role position already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/roles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createRole(@Valid @RequestBody CreateRoleResource resource) {
        log.debug("POST /api/v1/roles - position={}", resource.position());
        var result = roleCommandService.handle(ProfileAccessResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Role, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(ProfileAccessResourceAssembler.toResource(s.value()));
            case Result.Failure<Role, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get all roles")
    @GetMapping("/api/v1/roles")
    public ResponseEntity<List<RoleResource>> getAllRoles() {
        log.debug("GET /api/v1/roles");
        var result = roleQueryService.handle(new GetAllRolesQuery())
                .stream().map(ProfileAccessResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Get roles assigned to a user")
    @GetMapping("/api/v1/users/{userId}/roles")
    public ResponseEntity<List<RoleResource>> getRolesByUser(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        log.debug("GET /api/v1/users/{}/roles", userId);
        var result = roleQueryService.handle(new GetRolesByUserQuery(UserId.of(userId)))
                .stream().map(ProfileAccessResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Assign a role to a user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Role assigned"),
            @ApiResponse(responseCode = "404", description = "User or role not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "User already has this role",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/roles/assign", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignRoleToUser(@Valid @RequestBody AssignRoleResource resource) {
        log.debug("POST /api/v1/roles/assign - userId={} roleId={}", resource.userId(), resource.roleId());
        var result = roleCommandService.handle(ProfileAccessResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<UserRole, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).build();
            case Result.Failure<UserRole, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    // ── Permissions ───────────────────────────────────────────────────────────

    @Operation(summary = "Create a permission")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Permission created",
                    content = @Content(schema = @Schema(implementation = PermissionResource.class))),
            @ApiResponse(responseCode = "409", description = "Permission category already exists",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/permissions", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> createPermission(@Valid @RequestBody CreatePermissionResource resource) {
        log.debug("POST /api/v1/permissions - category={}", resource.category());
        var result = roleCommandService.handle(ProfileAccessResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<Permission, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(ProfileAccessResourceAssembler.toResource(s.value()));
            case Result.Failure<Permission, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get permissions assigned to a role")
    @GetMapping("/api/v1/roles/{roleId}/permissions")
    public ResponseEntity<List<PermissionResource>> getPermissionsByRole(
            @Parameter(description = "Role ID", required = true) @PathVariable Long roleId) {
        log.debug("GET /api/v1/roles/{}/permissions", roleId);
        var result = roleQueryService.handle(new GetPermissionsByRoleQuery(RoleId.of(roleId)))
                .stream().map(ProfileAccessResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "Assign a permission to a role")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Permission assigned"),
            @ApiResponse(responseCode = "404", description = "Role or permission not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "409", description = "Role already has this permission",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/api/v1/permissions/assign", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> assignPermissionToRole(@Valid @RequestBody AssignPermissionResource resource) {
        log.debug("POST /api/v1/permissions/assign - roleId={} permissionId={}", resource.roleId(), resource.permissionId());
        var result = roleCommandService.handle(ProfileAccessResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<RolePermission, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED).build();
            case Result.Failure<RolePermission, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

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