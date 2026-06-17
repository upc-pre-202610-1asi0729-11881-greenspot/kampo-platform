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
 * Aggregate root that represents a managed inventory item.
 *
 * <p>Responsible for:
 * <ul>
 *   <li>Tracking the current stock quantity and unit of measure.</li>
 *   <li>Evaluating whether stock has dropped to or below the minimum threshold.</li>
 *   <li>Publishing {@link InventoryCreatedEvent} upon creation.</li>
 * </ul>
 *
 * <p>Identity is carried by {@link InventoryId}. The raw {@code Long} surrogate key
 * is managed by the infrastructure persistence entity and injected via
 * {@code reconstitute()} — the aggregate itself never generates IDs.
 */
@Getter
public class Inventory extends AbstractDomainAggregateRoot<Inventory> {

    private InventoryId id;
    private String name;
    private int quantity;
    private String unit;
    private int minStock;
    private InventoryStatus status;

    /** Required by JPA proxy — do not use directly. */
    protected Inventory() {}

    /**
     * Reconstitution constructor — rebuilds the aggregate directly from
     * persisted values without triggering any domain logic or events.
     * Used exclusively by {@link com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.InventoryPersistenceAssembler}.
     *
     * @param id       the persisted surrogate key
     * @param name     the inventory item name
     * @param quantity current stock quantity
     * @param unit     unit of measure
     * @param minStock minimum stock threshold
     * @param status   current inventory status
     */
    public Inventory(Long id, String name, int quantity, String unit,
                     int minStock, InventoryStatus status) {
        this.id       = InventoryId.of(id);
        this.name     = name;
        this.quantity = quantity;
        this.unit     = unit;
        this.minStock = minStock;
        this.status   = status;
    }

    /**
     * Creates a new Inventory item from a {@link CreateInventoryCommand}.
     * Registers an {@link InventoryCreatedEvent} to be published after save.
     *
     * @param command the creation command carrying all required data
     */
    public Inventory(CreateInventoryCommand command) {
        this.name = command.name();
        this.quantity = command.quantity();
        this.unit = command.unit();
        this.minStock = command.minStock();
        this.status = InventoryStatus.from(command.quantity(), command.minStock());
        // Event will be published after the aggregate is persisted
        registerDomainEvent(new InventoryCreatedEvent(this));
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