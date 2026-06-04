package com.acme.kampo.platform.inventory.domain.model.aggregates;

import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;
import com.acme.kampo.platform.inventory.domain.model.events.OrderInputCreatedEvent;
import com.acme.kampo.platform.inventory.domain.model.valueObjects.InventoryId;
import com.acme.kampo.platform.inventory.domain.model.valueObjects.OrderId;
import com.acme.kampo.platform.inventory.domain.model.valueObjects.SupplierId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Aggregate root that represents a purchase order for inventory inputs.
 *
 * <p>Lifecycle:
 * <pre>
 *   new OrderInput(OrderInputCommand)  →  PENDING
 *   receive(ReceiveInputCommand)       →  RECEIVED  (terminal)
 * </pre>
 *
 * <p>Once RECEIVED or CANCELLED the order is immutable.
 * Identity is carried by {@link OrderId}; injected via {@code reconstitute()} after persistence.
 */
@Getter
public class OrderInput extends AbstractDomainAggregateRoot<OrderInput> {
    @Setter
    private OrderId id;
    private InventoryId inventoryId;
    private SupplierId supplierId;
    private int quantity;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private LocalDateTime receivedAt;

    /** Required by JPA proxy and reconstitution — do not use directly. */
    protected OrderInput() {}

    /**
     * Creates a OrderInput from the provided domain values.
     */
    public OrderInput(OrderId id, InventoryId inventoyId, SupplierId supplierId, Integer quantity, OrderStatus status, LocalDateTime orderedAt, LocalDateTime receivedAt) {
        this.id = id;
        this.inventoryId = inventoyId;
        this.supplierId = supplierId;
        this.quantity = quantity;
        this.status = status;
        this.orderedAt = orderedAt;
        this.receivedAt = receivedAt;
    }

    /**
     * Constructor with a OrderInputCommand.
     * @param command The {@link OrderInputCommand} instance
     */
    public OrderInput(OrderInputCommand command) {
        this.inventoryId = InventoryId.of(command.inventoryId());
        this.supplierId  = SupplierId.of(command.supplierId());
        this.quantity    = command.quantity();
        this.status      = OrderStatus.PENDING;
        this.orderedAt   = LocalDateTime.now();
        registerDomainEvent(new OrderInputCreatedEvent(
                null, command.inventoryId(), command.supplierId(), command.quantity()));
    }
    /**
     * Reconstitutes the OrderInput by binding its typed identity after persistence.
     *
     * @param rawId the surrogate key assigned by the database
     * @return this instance (fluent)
     */
    public OrderInput reconstitute(Long rawId) {
        this.id = OrderId.of(rawId);
        return this;
    }

    // ── Behavior ──────────────────────────────────────────────────────────────

    /**
     * Marks this order as received, recording the arrival date-time.
     * The caller (command service) is responsible for updating the inventory stock.
     *
     * @param command the receive command carrying the arrival date-time
     * @throws IllegalStateException if the order is already in a terminal state
     */
    public void receive(ReceiveInputCommand command) {
        if (status.isTerminal()) {
            throw new IllegalStateException(
                    "Cannot receive order — current status is already terminal: " + status);
        }
        this.status     = OrderStatus.RECEIVED;
        this.receivedAt = command.receivedAt();
    }

}
