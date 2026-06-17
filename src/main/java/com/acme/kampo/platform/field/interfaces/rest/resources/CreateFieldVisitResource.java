package com.acme.kampo.platform.field.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Inbound resource for scheduling a new field visit.
 */
@Schema(description = "Resource to schedule a new field visit")
public record CreateFieldVisitResource(

        @Schema(description = "ID of the field to visit", example = "1")
        Long fieldId,

        @Schema(description = "ID of the agent or agronomist who will carry out the visit", example = "1")
        Long agentId,

        @Schema(description = "Date and time the visit is scheduled for — must be in the future",
                example = "2025-07-15T09:00:00")
        LocalDateTime scheduledAt
) {}