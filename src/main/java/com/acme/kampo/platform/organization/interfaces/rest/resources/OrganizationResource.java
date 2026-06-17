package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing an Organization in API responses.
 */
@Schema(description = "Organization representation")
public record OrganizationResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Organization name", example = "Acme Agro SAC")
        String name,

        @Schema(description = "Physical address", example = "Av. La Mar 123, Lima")
        String address
) {}