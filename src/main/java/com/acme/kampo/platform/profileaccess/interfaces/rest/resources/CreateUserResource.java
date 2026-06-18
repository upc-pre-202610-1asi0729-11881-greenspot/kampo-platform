package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for registering a new user.
 */
@Schema(description = "Resource to register a new user")
public record CreateUserResource(

        @Schema(description = "User's first name", example = "Juan")
        String firstName,

        @Schema(description = "User's last name", example = "Pérez")
        String lastName,

        @Schema(description = "User's email address — must be unique", example = "juan.perez@acme.pe")
        String email,

        @Schema(description = "User's phone number", example = "+51999888777")
        String phone,

        @Schema(description = "Plain-text password — minimum 8 characters", example = "secret123")
        String password
) {}