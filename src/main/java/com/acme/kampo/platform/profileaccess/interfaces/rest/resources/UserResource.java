package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing a User in API responses.
 * Password hash is never included.
 */
@Schema(description = "User representation")
public record UserResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "First name", example = "Juan")
        String firstName,

        @Schema(description = "Last name", example = "Pérez")
        String lastName,

        @Schema(description = "Email address", example = "juan.perez@acme.pe")
        String email,

        @Schema(description = "Phone number", example = "+51999888777")
        String phone
) {}