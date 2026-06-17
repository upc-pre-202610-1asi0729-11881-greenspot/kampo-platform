package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities;

import java.time.LocalDateTime;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the OrderInput aggregate.
 *
 * <p>Extends {@link AuditableAbstractPersistenceEntity} to inherit {@code id},
 * {@code createdAt} and {@code updatedAt}.</p>
 *
 * <p>Translation handled by
 * {@link com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.OrderInputPersistenceAssembler}.</p>
 */
@Setter
@Getter
@Entity
@Table(name = "order_inputs")
public class OrderInputPersistenceEntity extends AuditableAbstractPersistenceEntity {

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

    public OrderInputPersistenceEntity() {}

}
