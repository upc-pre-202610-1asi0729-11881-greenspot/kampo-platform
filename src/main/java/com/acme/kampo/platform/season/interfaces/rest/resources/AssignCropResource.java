package com.acme.kampo.platform.season.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request resource to assign a crop to a season")
public record AssignCropResource(

        @Schema(description = "Crop ID", example = "1")
        @NotNull(message = "cropId must not be null")
        Long cropId) {
}