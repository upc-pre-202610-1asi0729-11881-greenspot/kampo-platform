package com.acme.kampo.platform.season.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

@Schema(description = "Request resource to end a season")
public record EndSeasonResource(

        @Schema(description = "End date", example = "2026-06-01")
        @NotNull(message = "endAt must not be null")
        LocalDate endAt) {
}