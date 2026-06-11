package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing a Fundo in API responses.
 */
@Schema(description = "Fundo representation")
public record FundoResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "ID of the owning organization", example = "1")
        Long organizationId,

        @Schema(description = "Fundo name", example = "Fundo San José")
        String name,

        @Schema(description = "Geographic latitude", example = "-12.0464")
        Double latitude,

        @Schema(description = "Geographic longitude", example = "-77.0428")
        Double longitude
) {}