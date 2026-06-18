package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for user authentication.
 */
@Schema(description = "Resource to authenticate a user and obtain a JWT token")
public record LoginResource(

        @Schema(description = "Registered email address", example = "juan.perez@acme.pe")
        String email,

        @Schema(description = "Plain-text password", example = "secret123")
        String password
) {}