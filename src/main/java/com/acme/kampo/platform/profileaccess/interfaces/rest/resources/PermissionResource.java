package com.acme.kampo.platform.profileaccess.interfaces.rest.resources;

import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;
import io.swagger.v3.oas.annotations.media.Schema;

/** Outbound resource representing a Permission. */
@Schema(description = "Permission representation")
public record PermissionResource(
        @Schema(description = "Unique identifier", example = "1") Long id,
        @Schema(description = "Permission category", example = "FINANCIAL") PermissionCategory category,
        @Schema(description = "Description", example = "View financial data") String description
) {}