package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers;


import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.command.OrderInputCommand;
import com.acme.kampo.platform.inventory.domain.model.command.ReceiveInputCommand;
import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.OrderInputPersistenceEntity;


/**
 * Static assembler between the {@link OrderInput} aggregate and its
 * JPA persistence representation {@link OrderInputPersistenceEntity}.
 */
public final class OrderInputPersistenceAssembler {

    private OrderInputPersistenceAssembler() {}

    public static OrderInput toDomainFromPersistence(OrderInputPersistenceEntity entity) {
        return new OrderInput(
                entity.getId(),
                entity.getInventoryId(),
                entity.getSupplierId(),
                entity.getQuantity(),
                entity.getStatus(),
                entity.getOrderedAt(),
                entity.getReceivedAt());
    }

    public static OrderInputPersistenceEntity toPersistenceFromDomain(OrderInput order) {
        var entity = new OrderInputPersistenceEntity();
        entity.setId(order.getId() != null ? order.getId().getValue() : null);
        entity.setInventoryId(order.getInventoryId().getValue());
        entity.setSupplierId(order.getSupplierId().getValue());
        entity.setQuantity(order.getQuantity());
        entity.setStatus(order.getStatus());
        entity.setOrderedAt(order.getOrderedAt());
        entity.setReceivedAt(order.getReceivedAt());
        return entity;
    }
}
