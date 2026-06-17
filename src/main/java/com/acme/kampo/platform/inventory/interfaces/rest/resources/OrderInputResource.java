package com.acme.kampo.platform.inventory.interfaces.rest.resources;

import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * Outbound resource representing an OrderInput in API responses.
 */
@Schema(description = "Input order representation")
public record OrderInputResource(

        @Schema(description = "Unique identifier of the order", example = "1")
        Long id,

        @Schema(description = "ID of the inventory item ordered", example = "1")
        Long inventoryId,

        @Schema(description = "ID of the supplier fulfilling the order", example = "1")
        Long supplierId,

        @Schema(description = "Number of units ordered", example = "50")
        int quantity,

        @Schema(description = "Current order status — PENDING, RECEIVED or CANCELLED",
                example = "PENDING")
        OrderStatus status,

        @Schema(description = "Date and time the order was placed",
                example = "2025-06-04T10:30:00")
        LocalDateTime orderedAt,

        @Schema(description = "Date and time the order was received — null if still PENDING",
                example = "2025-06-04T11:00:00", nullable = true)
        LocalDateTime receivedAt
) {}