package com.acme.kampo.platform.inventory.domain.model.aggregates;

import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.inventory.domain.model.events.SupplierCreatedEvent;
import com.acme.kampo.platform.inventory.domain.model.valueObjects.SupplierId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

/**
 * Supplier aggregate root.
 *
 * <p>Extends {@link AbstractDomainAggregateRoot} to gain domain event registration
 * support. No JPA or persistence annotation is present here -- those concerns live
 * exclusively in {@code SupplierPersistenceEntity}.</p>
 */
public class Supplier extends AbstractDomainAggregateRoot<Supplier> {

    @Getter
    @Setter
    private SupplierId id;
    @Getter
    private String name;
    @Getter
    private String contact;
    @Getter
    private String email;

    protected Supplier(){}

    /**
     * Creates a supplier from the provided domain values.
     */
    public Supplier(SupplierId id, String name, String contact, String email) {
        this.id = id;
        this.name =  Objects.requireNonNull(name, "Supplier name cannot be null.");
        this.contact =  Objects.requireNonNull(contact, "Supplier contact cannot be null.");
        this.email = Objects.requireNonNull(email, "Supplier email cannot be null.") ;
    }
    /**
     * Constructor with a CreateSupplerCommand.
     * @param command The {@link AddSupplierCommand} instance
     */
    public Supplier(AddSupplierCommand command) {
        this.name = command.name();
        this.contact = command.contact();
        this.email = command.email();
        registerDomainEvent(new SupplierCreatedEvent(null,name,email));

    }
    /**
     * Reconstitutes the Supplier by binding its typed identity after persistence.
     *
     * @param rawId the surrogate key assigned by the database
     * @return this instance (fluent)
     */
    public Supplier reconstitute(Long rawId) {
        this.id = SupplierId.of(rawId);
        return this;
    }

}
