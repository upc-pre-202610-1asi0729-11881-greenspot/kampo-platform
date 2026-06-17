package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for updating an existing organization.
 */
@Schema(description = "Resource to update an existing organization")
public record UpdateOrganizationResource(

        @Schema(description = "Updated name", example = "Nuevo Nombre SAC")
        String name,

        @Schema(description = "Updated address", example = "Av. Javier Prado 456, Lima")
        String address
) {}
