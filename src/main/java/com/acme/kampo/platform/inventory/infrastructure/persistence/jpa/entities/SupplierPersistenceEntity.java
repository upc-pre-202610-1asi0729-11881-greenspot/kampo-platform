package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the Supplier aggregate.
 *
 * <p>Extends {@link AuditableAbstractPersistenceEntity} to inherit {@code id},
 * {@code createdAt} and {@code updatedAt}.</p>
 *
 * <p>Translation handled by
 * {@link com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.SupplierPersistenceAssembler}.</p>
 */
@Setter
@Getter
@Entity
@Table(name = "suppliers")
public class SupplierPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, length = 50)
    private String contact;

    @Column(nullable = false, length = 100)
    private String email;

    public SupplierPersistenceEntity() {}

}
