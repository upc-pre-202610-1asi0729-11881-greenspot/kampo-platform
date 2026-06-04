package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * JPA persistence entity for the {@link Inventory} aggregate.
 *
 * <p>This class is an infrastructure concern only — it knows about tables,
 * columns, and JPA annotations. The domain aggregate knows nothing about this class.
 *
 * <p>Mapping strategy:
 * <ul>
 *   <li>{@code toDomainModel()} reconstructs a fully hydrated {@link Inventory} aggregate.</li>
 *   <li>{@code fromDomainModel()} produces a persistence entity ready to be saved.</li>
 * </ul>
 */
@Getter
@Entity
@Table(name = "inventories")
public class InventoryPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

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

    /** Required by JPA — do not use directly. */
    protected InventoryPersistenceEntity() {}

    private InventoryPersistenceEntity(Long id, String name, int quantity,
                                       String unit, int minStock, InventoryStatus status) {
        this.id       = id;
        this.name     = name;
        this.quantity = quantity;
        this.unit     = unit;
        this.minStock = minStock;
        this.status   = status;
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    /**
     * Reconstructs the {@link Inventory} aggregate from this persistence entity.
     * Uses a minimal {@link CreateInventoryCommand} to re-create the aggregate state,
     * then calls {@code reconstitute()} to bind the real database ID.
     */
    public Inventory toDomainModel() {
        var command = new CreateInventoryCommand(name, quantity, unit, minStock);
        var inventory = new Inventory(command);
        // Clear the domain event registered during construction — this is a
        // reconstitution from persistence, not a fresh creation.
        inventory.clearDomainEvents();
        return inventory.reconstitute(id);
    }

    /**
     * Produces a persistence entity from an {@link Inventory} aggregate.
     * If the aggregate has no ID yet (new), {@code id} will be null and
     * JPA will assign it on insert.
     */
    public static InventoryPersistenceEntity fromDomainModel(Inventory inventory) {
        Long rawId = (inventory.getId() != null) ? inventory.getId().getValue() : null;
        return new InventoryPersistenceEntity(
                rawId,
                inventory.getName(),
                inventory.getQuantity(),
                inventory.getUnit(),
                inventory.getMinStock(),
                inventory.getStatus()
        );
    }

}