package com.acme.kampo.platform.inventory.domain.model.enums;

/**
 * Represents the lifecycle state of an OrderInput.
 *
 * <p>Valid transitions:
 * <pre>
 *   PENDING → RECEIVED   (via OrderInput#receive)
 *   PENDING → CANCELLED  (via OrderInput#cancel — future use)
 * </pre>
 * A RECEIVED or CANCELLED order is terminal and cannot be modified.
 */
public enum OrderStatus {
    /**
     * The order has been placed but goods have not yet arrived.
     */
    PENDING,
    /**
     * The order has been fulfilled — stock was updated upon receipt.
     */
    RECEIVED,
    /**
     * The order was cancelled before receipt — stock was not affected.
     */
    CANCELLED;
    /**
     * Returns true if this status is terminal (no further transitions allowed).
     */
    public boolean isTerminal(){
        return this == RECEIVED || this == CANCELLED;
    }
}
