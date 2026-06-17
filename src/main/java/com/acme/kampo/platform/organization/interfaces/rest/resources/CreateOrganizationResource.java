package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for creating a new organization.
 */
@Schema(description = "Resource to create a new organization")
public record CreateOrganizationResource(

        @Schema(description = "Organization name — must be unique", example = "Acme Agro SAC")
        String name,

        @Schema(description = "Physical address of the organization", example = "Av. La Mar 123, Lima")
        String address
) {}