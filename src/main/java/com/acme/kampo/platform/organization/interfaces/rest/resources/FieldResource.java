package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing a Field in API responses.
 */
@Schema(description = "Field representation")
public record FieldResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "ID of the owning fundo", example = "1")
        Long fundoId,

        @Schema(description = "Field name", example = "Parcela Norte")
        String name,

        @Schema(description = "Field area in square metres", example = "5000.0")
        Double areaSqm
) {}
