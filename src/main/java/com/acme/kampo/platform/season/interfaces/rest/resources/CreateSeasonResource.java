package com.acme.kampo.platform.season.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Request resource to create a season")
public record CreateSeasonResource(

        @Schema(description = "Field ID", example = "1")
        @NotNull(message = "fieldId must not be null")
        Long fieldId,

        @Schema(description = "Crop name", example = "Tomato")
        @NotBlank(message = "cropName must not be blank")
        String cropName,

        @Schema(description = "Start date", example = "2024-01-01")
        @NotNull(message = "startAt must not be null")
        LocalDate startAt) {
}