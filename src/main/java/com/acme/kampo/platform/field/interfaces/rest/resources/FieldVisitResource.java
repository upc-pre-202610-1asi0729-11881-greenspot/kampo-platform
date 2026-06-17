package com.acme.kampo.platform.field.interfaces.rest.resources;

import com.acme.kampo.platform.field.domain.model.enums.FieldVisitStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Outbound resource representing a FieldVisit in API responses.
 */
@Schema(description = "Field visit representation")
public record FieldVisitResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "ID of the visited field", example = "1")
        Long fieldId,

        @Schema(description = "ID of the agent who carried out the visit", example = "1")
        Long agentId,

        @Schema(description = "Scheduled date and time", example = "2025-07-15T09:00:00")
        LocalDateTime scheduledAt,

        @Schema(description = "Date and time the visit was completed — null if not yet done",
                example = "2025-07-15T11:30:00")
        LocalDateTime doneAt,

        @Schema(description = "Current status of the visit", example = "SCHEDULED")
        FieldVisitStatus status
) {}