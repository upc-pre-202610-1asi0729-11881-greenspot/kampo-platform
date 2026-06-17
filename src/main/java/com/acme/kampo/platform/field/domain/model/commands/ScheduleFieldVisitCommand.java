package com.acme.kampo.platform.field.domain.model.commands;

import java.time.LocalDateTime;

/**
 * Command to schedule a new field visit.
 *
 * @param fieldId     ID of the field to visit
 * @param agentId     ID of the agent or agronomist who will carry out the visit
 * @param scheduledAt date and time the visit is scheduled for — must be in the future
 */
public record ScheduleFieldVisitCommand(Long fieldId, Long agentId, LocalDateTime scheduledAt) {
    public ScheduleFieldVisitCommand {
        if (fieldId == null || fieldId <= 0)
            throw new IllegalArgumentException("fieldId must be positive");
        if (agentId == null || agentId <= 0)
            throw new IllegalArgumentException("agentId must be positive");
        if (scheduledAt == null)
            throw new IllegalArgumentException("scheduledAt must not be null");
        if (scheduledAt.isBefore(LocalDateTime.now()))
            throw new IllegalArgumentException("scheduledAt must be in the future");
    }
}