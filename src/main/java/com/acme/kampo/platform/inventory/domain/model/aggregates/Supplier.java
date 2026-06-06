package com.acme.kampo.platform.inventory.domain.model.aggregates;

import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.inventory.domain.model.events.SupplierCreatedEvent;
import com.acme.kampo.platform.inventory.domain.model.valueObjects.SupplierId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

/**
 * Aggregate root that represents a supplier of inventory inputs.
 */
@Getter
public class Supplier extends AbstractDomainAggregateRoot<Supplier> {

    private SupplierId id;
    private String name;
    private String contact;
    private String email;

    /** Required by JPA proxy — do not use directly. */
    protected Supplier() {}

    /**
     * Reconstitution constructor — rebuilds the aggregate directly from
     * persisted values without triggering any domain logic or events.
     * Used exclusively by {@link com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.SupplierPersistenceAssembler}.
     */
    public Supplier(Long id, String name, String contact, String email) {
        this.id      = SupplierId.of(id);
        this.name    = name;
        this.contact = contact;
        this.email   = email;
    }

    /**
     * Creates a new Supplier from an {@link AddSupplierCommand}.
     * Registers a {@link SupplierCreatedEvent} to be published after save.
     */
    public Supplier(AddSupplierCommand command) {
        this.name    = command.name();
        this.contact = command.contact();
        this.email   = command.email();
        registerDomainEvent(new SupplierCreatedEvent(this));
    }

    public Supplier reconstitute(Long rawId) {
        this.id = SupplierId.of(rawId);
        return this;
    }

}
