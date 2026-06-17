package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for registering a new fundo.
 */
@Schema(description = "Resource to register a new fundo")
public record RegisterFundoResource(

        @Schema(description = "ID of the organization this fundo belongs to", example = "1")
        Long organizationId,

        @Schema(description = "Fundo name", example = "Fundo San José")
        String name,

        @Schema(description = "Geographic latitude in decimal degrees", example = "-12.0464")
        Double latitude,

        @Schema(description = "Geographic longitude in decimal degrees", example = "-77.0428")
        Double longitude
) {}
