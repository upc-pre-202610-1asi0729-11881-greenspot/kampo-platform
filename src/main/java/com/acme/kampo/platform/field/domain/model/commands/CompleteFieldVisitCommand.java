package com.acme.kampo.platform.field.domain.model.commands;

/**
 * Command to mark an existing field visit as completed.
 * Completing a visit is terminal — it cannot be undone.
 *
 * @param fieldVisitId ID of the field visit to complete
 */
public record CompleteFieldVisitCommand(Long fieldVisitId) {
    public CompleteFieldVisitCommand {
        if (fieldVisitId == null || fieldVisitId <= 0)
            throw new IllegalArgumentException("fieldVisitId must be positive");
    }
}