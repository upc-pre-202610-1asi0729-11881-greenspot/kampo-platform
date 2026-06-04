package com.acme.kampo.platform.inventory.domain.model.aggregates;

import com.acme.kampo.platform.inventory.domain.model.command.CreateInventoryCommand;
import com.acme.kampo.platform.inventory.domain.model.command.UpdateStockCommand;
import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;
import com.acme.kampo.platform.inventory.domain.model.events.InventoryCreatedEvent;
import com.acme.kampo.platform.inventory.domain.model.valueObjects.InventoryId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Inventory aggregate root.
 *
 * <p>Extends {@link AbstractDomainAggregateRoot} to gain domain event registration
 * support. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in {@code InventoryPersistenceEntity}.</p>
 */
public class Inventory extends AbstractDomainAggregateRoot<Inventory> {
    @Getter
    @Setter
    private InventoryId id;
    @Getter
    @Setter
    private String name;
    @Getter
    @Setter
    private int quantity;
    @Getter
    @Setter
    private String unit;
    @Getter
    @Setter
    private int minStock;
    @Getter
    @Setter
    private InventoryStatus status;

    /** Required by JPA proxy and reconstitution — do not use directly. */
    protected Inventory() {}

    /**
     * Creates a Inventory from the provided domain values.
     */
    public Inventory(InventoryId id, String name, Integer quantity, String unit, Integer minStock, InventoryStatus status) {
        this.id       = id;
        this.name     = Objects.requireNonNull(name, "name must not be null");
        this.quantity = Objects.requireNonNull(quantity, "quantity must not be null");
        this.unit     = Objects.requireNonNull(unit, "unit must not be null");
        this.minStock = Objects.requireNonNull(minStock, "minStock must not be null");
        this.status   = Objects.requireNonNull(status, "status must not be null");
    }
    /**
     * Creates a Inventory from the provided domain values.
     */
    public Inventory(String name, Integer quantity, String unit, Integer minStock,InventoryStatus status) {
        this(null, name, quantity, unit, minStock, status);
    }

    /**
     * Constructor with a CreateInventoryCommand.
     * @param command The {@link CreateInventoryCommand} instance
     */
    public Inventory(CreateInventoryCommand command){
        this.name= command.name();
        this.quantity = command.quantity();
        this.unit = command.unit();
        this.minStock = command.minStock();
        this.status = InventoryStatus.from(command.quantity(),command.minStock());
        registerDomainEvent(new InventoryCreatedEvent(null,name,quantity,unit));
    }
    /**
     * Reconstitutes an Inventory from persistence, binding its typed identity.
     * Called by the infrastructure adapter after saving, so the real ID is known.
     *
     * @param rawId the surrogate key assigned by the database
     * @return this instance (fluent)
     */
    public Inventory reconstitute(Long rawId) {
        this.id = InventoryId.of(rawId);
        return this;
    }
    // ── Behavior ──────────────────────────────────────────────────────────────

    /**
     * Adjusts the stock quantity by the given delta (positive = add, negative = remove).
     * Re-evaluates and updates the status after the change.
     *
     * @param command the stock update command
     * @throws IllegalStateException if the resulting quantity would go negative
     */
    public void updateStock(UpdateStockCommand command) {
        int newQuantity = this.quantity + command.delta();
        if (newQuantity < 0) {
            throw new IllegalStateException(
                    "Cannot remove %d units from inventory '%s': only %d available"
                            .formatted(Math.abs(command.delta()), name, quantity));
        }
        this.quantity = newQuantity;
        this.status = InventoryStatus.from(this.quantity, this.minStock);
    }
     /**
     * Evaluates whether current stock is at or below the minimum threshold.
     *
     * @return {@code true} if status is LOW_STOCK or OUT_OF_STOCK
     */
    public boolean evaluateMinStock() {
        return status == InventoryStatus.LOW_STOCK || status == InventoryStatus.OUT_OF_STOCK;
    }




}
