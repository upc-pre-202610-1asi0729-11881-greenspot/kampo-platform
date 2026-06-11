package com.acme.kampo.platform.organization.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

/**
 * Outbound resource representing a Crop in API responses.
 */
@Schema(description = "Crop representation")
public record CropResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "ID of the field this crop is planted in", example = "1")
        Long fieldId,

        @Schema(description = "Crop name", example = "Papa Amarilla")
        String name,

        @Schema(description = "Date the crop was planted", example = "2025-04-15")
        LocalDate plantedAt
) {}