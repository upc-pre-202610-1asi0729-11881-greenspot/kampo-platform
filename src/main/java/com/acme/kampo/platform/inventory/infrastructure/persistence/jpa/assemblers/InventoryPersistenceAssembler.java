package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.InventoryPersistenceEntity;
/**
 * Static assembler between the {@link Inventory} aggregate and its
 * JPA persistence representation {@link InventoryPersistenceEntity}.
 */
public final class InventoryPersistenceAssembler {

    private InventoryPersistenceAssembler() {}

    public static Inventory toDomainFromPersistence(InventoryPersistenceEntity entity) {
        return new Inventory(
                entity.getId(),
                entity.getName(),
                entity.getQuantity(),
                entity.getUnit(),
                entity.getMinStock(),
                entity.getStatus());
    }

    public static InventoryPersistenceEntity toPersistenceFromDomain(Inventory inventory) {
        var entity = new InventoryPersistenceEntity();
        entity.setId(inventory.getId() != null ? inventory.getId().getValue() : null);
        entity.setName(inventory.getName());
        entity.setQuantity(inventory.getQuantity());
        entity.setUnit(inventory.getUnit());
        entity.setMinStock(inventory.getMinStock());
        entity.setStatus(inventory.getStatus());
        return entity;
    }
}