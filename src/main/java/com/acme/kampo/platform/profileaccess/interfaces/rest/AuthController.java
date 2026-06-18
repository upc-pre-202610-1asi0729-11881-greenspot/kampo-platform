package com.acme.kampo.platform.profileaccess.interfaces.rest;

import com.acme.kampo.platform.profileaccess.application.commandservices.AuthCommandService;
import com.acme.kampo.platform.profileaccess.interfaces.rest.resources.AuthTokenResource;
import com.acme.kampo.platform.profileaccess.interfaces.rest.resources.LoginResource;
import com.acme.kampo.platform.profileaccess.interfaces.rest.transform.ProfileAccessResourceAssembler;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import io.swagger.v3.oas.annotations.Operation;
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

@Slf4j
@RestController
@RequestMapping(value = "/api/v1/auth", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

    private final AuthCommandService authCommandService;

    public AuthController(AuthCommandService authCommandService) {
        this.authCommandService = authCommandService;
    }

    @Operation(summary = "Login",
            description = "Authenticates a user and returns a JWT Bearer token")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Authentication successful",
                    content = @Content(schema = @Schema(implementation = AuthTokenResource.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> login(@Valid @RequestBody LoginResource resource) {
        log.debug("POST /api/v1/auth/login - email={}", resource.email());
        var result = authCommandService.handle(ProfileAccessResourceAssembler.toCommand(resource));
        return switch (result) {
            case Result.Success<String, ApplicationError> s ->
                    ResponseEntity.ok(AuthTokenResource.of(s.value()));
            case Result.Failure<String, ApplicationError> f -> {
                var problem = ProblemDetail.forStatus(HttpStatus.UNAUTHORIZED);
                problem.setTitle(f.error().code());
                problem.setDetail(f.error().message());
                yield ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(problem);
            }
        };
    }
}