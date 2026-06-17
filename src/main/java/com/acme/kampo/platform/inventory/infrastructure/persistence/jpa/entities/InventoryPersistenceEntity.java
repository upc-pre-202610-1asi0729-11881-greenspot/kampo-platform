package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the Inventory aggregate.
 *
 * <p>Extends {@link AuditableAbstractPersistenceEntity} to inherit {@code id},
 * {@code createdAt} and {@code updatedAt} — no need to redeclare {@code @Id}.</p>
 *
 * <p>Mapping concerns only — translation to/from the domain aggregate is handled
 * by {@link com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.InventoryPersistenceAssembler}.</p>
 */
@Setter
@Getter
@Entity
@Table(name = "inventories")
public class InventoryPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false, length = 20)
    private String unit;

    @Column(name = "min_stock", nullable = false)
    private int minStock;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private InventoryStatus status;

    public InventoryPersistenceEntity() {}

}
