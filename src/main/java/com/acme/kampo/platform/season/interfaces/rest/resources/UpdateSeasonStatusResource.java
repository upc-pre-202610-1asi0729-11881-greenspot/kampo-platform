package com.acme.kampo.platform.season.interfaces.rest.resources;

import com.acme.kampo.platform.season.domain.model.enums.SeasonStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Request resource to update season status")
public record UpdateSeasonStatusResource(

        @Schema(description = "New status", example = "GROWING")
        @NotNull(message = "status must not be null")
        SeasonStatus status) {
}