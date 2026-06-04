package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * JPA persistence entity for the {@link Supplier} aggregate.
 */
@Getter
@Entity
@Table(name = "suppliers")
public class SupplierPersistenceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 50)
    private String contact;

    @Column(nullable = false, length = 100)
    private String email;

    protected SupplierPersistenceEntity() {}

    private SupplierPersistenceEntity(Long id, String name, String contact, String email) {
        this.id      = id;
        this.name    = name;
        this.contact = contact;
        this.email   = email;
    }

    // ── Mapping ───────────────────────────────────────────────────────────────

    public Supplier toDomainModel() {
        var command = new AddSupplierCommand(name, contact, email);
        var supplier = new Supplier(command);
        supplier.clearDomainEvents();
        return supplier.reconstitute(id);
    }

    public static SupplierPersistenceEntity fromDomainModel(Supplier supplier) {
        Long rawId = (supplier.getId() != null) ? supplier.getId().getValue() : null;
        return new SupplierPersistenceEntity(
                rawId,
                supplier.getName(),
                supplier.getContact(),
                supplier.getEmail()
        );
    }

}
