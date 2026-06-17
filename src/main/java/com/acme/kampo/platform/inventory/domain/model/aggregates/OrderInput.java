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

import java.time.LocalDateTime;

/**
 * Aggregate root that represents a purchase order for inventory inputs.
 */
@Getter
public class OrderInput extends AbstractDomainAggregateRoot<OrderInput> {

    private OrderId id;
    private InventoryId inventoryId;
    private SupplierId supplierId;
    private int quantity;
    private OrderStatus status;
    private LocalDateTime orderedAt;
    private LocalDateTime receivedAt;

    /** Required by JPA proxy — do not use directly. */
    protected OrderInput() {}

    /**
     * Reconstitution constructor — rebuilds the aggregate directly from
     * persisted values without triggering any domain logic or events.
     * Used exclusively by {@link com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.OrderInputPersistenceAssembler}.
     */
    public OrderInput(Long id, Long inventoryId, Long supplierId,
                      int quantity, OrderStatus status,
                      LocalDateTime orderedAt, LocalDateTime receivedAt) {
        this.id          = OrderId.of(id);
        this.inventoryId = InventoryId.of(inventoryId);
        this.supplierId  = SupplierId.of(supplierId);
        this.quantity    = quantity;
        this.status      = status;
        this.orderedAt   = orderedAt;
        this.receivedAt  = receivedAt;
    }

    /**
     * Places a new input order from an {@link OrderInputCommand}.
     * Status is set to PENDING and {@link OrderInputCreatedEvent} is registered.
     */
    public OrderInput(OrderInputCommand command) {
        this.inventoryId = InventoryId.of(command.inventoryId());
        this.supplierId  = SupplierId.of(command.supplierId());
        this.quantity    = command.quantity();
        this.status      = OrderStatus.PENDING;
        this.orderedAt   = LocalDateTime.now();
        registerDomainEvent(new OrderInputCreatedEvent(this));
    }

    public OrderInput reconstitute(Long rawId) {
        this.id = OrderId.of(rawId);
        return this;
    }

    public void receive(ReceiveInputCommand command) {
        if (status.isTerminal()) {
            throw new IllegalStateException(
                    "Cannot receive order — current status is already terminal: " + status);
        }
        this.status     = OrderStatus.RECEIVED;
        this.receivedAt = command.receivedAt();
    }

}