package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for updating a user's profile.
 * Email cannot be changed — only name and phone.
 */
@Schema(description = "Resource to update a user's profile")
public record UpdateUserResource(

        @Schema(description = "Updated first name", example = "Carlos")
        String firstName,

        @Schema(description = "Updated last name", example = "López")
        String lastName,

        @Schema(description = "Updated phone number", example = "+51111222333")
        String phone
) {}