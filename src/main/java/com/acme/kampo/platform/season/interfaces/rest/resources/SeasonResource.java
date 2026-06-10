package com.acme.kampo.platform.season.interfaces.rest.resources;

import com.acme.kampo.platform.season.domain.model.enums.SeasonStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDate;

@Schema(description = "Response resource representing a season")
public record SeasonResource(

        @Schema(description = "Season ID", example = "1")
        Long id,

        @Schema(description = "Field ID", example = "1")
        Long fieldId,

        @Schema(description = "Crop ID", example = "1")
        Long cropId,

        @Schema(description = "Crop name", example = "Tomato")
        String cropName,

        @Schema(description = "Season status")
        SeasonStatus status,

        @Schema(description = "Start date", example = "2024-01-01")
        LocalDate startedAt,

        @Schema(description = "End date", example = "2024-06-01")
        LocalDate endedAt) {
}