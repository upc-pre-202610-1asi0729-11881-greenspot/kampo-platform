package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for creating a new role.
 */
@Schema(description = "Resource to create a new role")
public record CreateRoleResource(

        @Schema(description = "Role position", example = "AGRONOMIST")
        RolePosition position,

        @Schema(description = "Description of the role's responsibilities",
                example = "Manages field operations and observations")
        String description
) {}