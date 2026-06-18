package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/** Inbound resource for assigning a role to a user. */
@Schema(description = "Resource to assign a role to a user")
public record AssignRoleResource(
        @Schema(description = "User ID", example = "1") Long userId,
        @Schema(description = "Role ID", example = "2") Long roleId
) {}