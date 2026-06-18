package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing a Role in API responses.
 */
@Schema(description = "Role representation")
public record RoleResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Role position", example = "AGRONOMIST")
        RolePosition position,

        @Schema(description = "Role description", example = "Manages field operations")
        String description
) {}