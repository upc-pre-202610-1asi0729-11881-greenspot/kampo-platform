package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

/** Inbound resource for creating a permission. */
@Schema(description = "Resource to create a new permission")
public record CreatePermissionResource(

        @Schema(description = "Permission category", example = "FINANCIAL")
        PermissionCategory category,

        @Schema(description = "Description of what the permission allows",
                example = "View financial reports and profitability data")
        String description
) {}