package com.acme.kampo.platform.alert.domain.model.commands;

/**
 * Command to mark an existing alert as read.
 *
 * @param alertId the ID of the alert to mark as read
 */
public record MarkAlertReadCommand(Long alertId) {
    public MarkAlertReadCommand {
        if (alertId == null || alertId <= 0)
            throw new IllegalArgumentException("alertId must be positive");
    }
}
