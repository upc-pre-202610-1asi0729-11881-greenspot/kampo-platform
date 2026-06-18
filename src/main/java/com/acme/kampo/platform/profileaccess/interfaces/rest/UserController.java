package com.acme.kampo.platform.profileaccess.interfaces.rest;

import com.acme.kampo.platform.profileaccess.application.commandservices.UserCommandService;
import com.acme.kampo.platform.profileaccess.application.queryservices.UserQueryService;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import com.acme.kampo.platform.profileaccess.domain.model.queries.*;
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
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "Endpoints for user registration and profile management")
public class UserController {

    private final UserCommandService userCommandService;
    private final UserQueryService   userQueryService;

    public UserController(UserCommandService userCommandService,
                          UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService   = userQueryService;
    }

    @Operation(summary = "Register a new user",
            description = "Creates a user, hashes the password and assigns the default AGRONOMIST role")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "User registered",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "409", description = "Email already registered",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> registerUser(@Valid @RequestBody CreateUserResource resource) {
        log.debug("POST /api/v1/users - email={}", resource.email());
        var result = userCommandService.handle(ProfileAccessResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<User, ApplicationError> s ->
                    ResponseEntity.status(HttpStatus.CREATED)
                            .body(ProfileAccessResourceAssembler.toResource(s.value()));
            case Result.Failure<User, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Update user profile",
            description = "Updates firstName, lastName and phone. Email cannot be changed.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Profile updated",
                    content = @Content(schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> modifyProfile(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId,
            @Valid @RequestBody UpdateUserResource resource) {
        log.debug("PUT /api/v1/users/{}", userId);
        var result = userCommandService.handle(
                ProfileAccessResourceAssembler.toCommand(userId, resource));
        return switch (result) {
            case Result.Success<User, ApplicationError> s ->
                    ResponseEntity.ok(ProfileAccessResourceAssembler.toResource(s.value()));
            case Result.Failure<User, ApplicationError> f -> toErrorResponse(f.error());
        };
    }

    @Operation(summary = "Get a user by ID")
    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(
            @Parameter(description = "User ID", required = true) @PathVariable Long userId) {
        log.debug("GET /api/v1/users/{}", userId);
        var result = userQueryService.handle(new GetUserByIdQuery(UserId.of(userId)));
        if (result.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(ProfileAccessResourceAssembler.toResource(result.get()));
    }

    @Operation(summary = "Get all users")
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        log.debug("GET /api/v1/users");
        var result = userQueryService.handle(new GetAllUsersQuery())
                .stream().map(ProfileAccessResourceAssembler::toResource).toList();
        return ResponseEntity.ok(result);
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