package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Inbound resource for registering a new crop.
 */
@Schema(description = "Resource to register a new crop within a field")
public record RegisterCropResource(

        @Schema(description = "ID of the field this crop is planted in", example = "1")
        Long fieldId,

        @Schema(description = "Crop name", example = "Papa Amarilla")
        String name,

        @Schema(description = "Date the crop was planted — must not be in the future", example = "2025-04-15")
        LocalDate plantedAt
) {}