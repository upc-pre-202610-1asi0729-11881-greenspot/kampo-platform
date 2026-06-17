package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;
/**
 * Static assembler between the {@link Supplier} aggregate and its
 * JPA persistence representation {@link SupplierPersistenceEntity}.
 */
public final class SupplierPersistenceAssembler {

    private SupplierPersistenceAssembler() {}

    public static Supplier toDomainFromPersistence(SupplierPersistenceEntity entity) {
        return new Supplier(
                entity.getId(),
                entity.getName(),
                entity.getContact(),
                entity.getEmail());
    }

    public static SupplierPersistenceEntity toPersistenceFromDomain(Supplier supplier) {
        var entity = new SupplierPersistenceEntity();
        entity.setId(supplier.getId() != null ? supplier.getId().getValue() : null);
        entity.setName(supplier.getName());
        entity.setContact(supplier.getContact());
        entity.setEmail(supplier.getEmail());
        return entity;
    }
}