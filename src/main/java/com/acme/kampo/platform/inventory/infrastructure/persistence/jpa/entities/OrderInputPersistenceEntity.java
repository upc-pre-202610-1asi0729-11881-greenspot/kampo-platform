package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * JPA persistence entity for the {@link OrderInput} aggregate.
 */
@Getter
@Entity
@Table(name = "order_inputs")
public class OrderInputPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "inventory_id", nullable = false)
    private Long inventoryId;

    @Column(name = "supplier_id", nullable = false)
    private Long supplierId;

    @Column(nullable = false)
    private int quantity;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private OrderStatus status;

    @Column(name = "ordered_at", nullable = false)
    private LocalDateTime orderedAt;

    @Column(name = "received_at")
    private LocalDateTime receivedAt;

    protected OrderInputPersistenceEntity() {}

    private OrderInputPersistenceEntity(Long id, Long inventoryId, Long supplierId,
                                        int quantity, OrderStatus status,
                                        LocalDateTime orderedAt, LocalDateTime receivedAt) {
        this.id          = id;
        this.inventoryId = inventoryId;
        this.supplierId  = supplierId;
        this.quantity    = quantity;
        this.status      = status;
        this.orderedAt   = orderedAt;
        this.receivedAt  = receivedAt;
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    /**
     * Reconstructs an {@link OrderInput} aggregate from this persistence entity.
     * If the order was already received, applies the receive transition on top.
     */
    public OrderInput toDomainModel() {
        var orderCmd = new OrderInputCommand(inventoryId, supplierId, quantity);
        var order = new OrderInput(orderCmd);
        order.clearDomainEvents();
        order.reconstitute(id);

        // Re-apply state transitions that happened after creation
        if (status == OrderStatus.RECEIVED && receivedAt != null) {
            order.receive(new ReceiveInputCommand(id, receivedAt));
        }
        return order;
    }

    public static OrderInputPersistenceEntity fromDomainModel(OrderInput order) {
        Long rawId = (order.getId() != null) ? order.getId().getValue() : null;
        return new OrderInputPersistenceEntity(
                rawId,
                order.getInventoryId().getValue(),
                order.getSupplierId().getValue(),
                order.getQuantity(),
                order.getStatus(),
                order.getOrderedAt(),
                order.getReceivedAt()
        );
    }
}
