package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for registering a new field.
 */
@Schema(description = "Resource to register a new field within a fundo")
public record RegisterFieldResource(

        @Schema(description = "ID of the fundo this field belongs to", example = "1")
        Long fundoId,

        @Schema(description = "Field name", example = "Parcela Norte")
        String name,

        @Schema(description = "Field area in square metres — must be positive", example = "5000.0")
        Double areaSqm
) {}