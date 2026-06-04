package com.acme.kampo.platform.inventory.interfaces.rest.resources;

import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;

import java.time.LocalDateTime;

/**
 * Outbound resource representing an OrderInput in API responses.
 */
public record OrderInputResource(
        Long id,
        Long inventoryId,
        Long supplierId,
        int quantity,
        OrderStatus status,
        LocalDateTime orderedAt,
        LocalDateTime receivedAt
) {}
