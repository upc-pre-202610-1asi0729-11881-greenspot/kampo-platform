package com.acme.kampo.platform.inventory.domain.model.command;

import java.time.LocalDateTime;

/**
 * Command to mark a pending order as received and update stock accordingly.
 *
 * @param orderId    ID of the order being received
 * @param receivedAt the date-time the goods physically arrived
 */
public record ReceiveInputCommand(
        Long orderId,
        LocalDateTime receivedAt
) {
    public ReceiveInputCommand {
        if (orderId == null || orderId <= 0)
            throw new IllegalArgumentException("orderId must be positive");
        if (receivedAt == null)
            throw new IllegalArgumentException("receivedAt must not be null");
        if (receivedAt.isAfter(LocalDateTime.now().plusMinutes(1)))
            throw new IllegalArgumentException("receivedAt cannot be in the future");
    }
}