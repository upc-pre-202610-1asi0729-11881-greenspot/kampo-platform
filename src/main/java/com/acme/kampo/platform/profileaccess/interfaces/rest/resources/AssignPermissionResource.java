package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/** Inbound resource for assigning a permission to a role. */
@Schema(description = "Resource to assign a permission to a role")
public record AssignPermissionResource(
        @Schema(description = "Role ID", example = "1") Long roleId,
        @Schema(description = "Permission ID", example = "3") Long permissionId
) {}