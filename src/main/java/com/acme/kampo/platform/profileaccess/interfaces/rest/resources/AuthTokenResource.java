package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource carrying the JWT token after successful authentication.
 */
@Schema(description = "JWT authentication token")
public record AuthTokenResource(

        @Schema(description = "JWT bearer token", example = "eyJhbGciOiJIUzI1NiJ9...")
        String token,

        @Schema(description = "Token type — always Bearer", example = "Bearer")
        String type
) {
    public static AuthTokenResource of(String token) {
        return new AuthTokenResource(token, "Bearer");
    }
}