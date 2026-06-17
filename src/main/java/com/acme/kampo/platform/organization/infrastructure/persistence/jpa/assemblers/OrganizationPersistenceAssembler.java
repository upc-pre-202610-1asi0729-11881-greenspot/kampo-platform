package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.organization.domain.model.aggregates.Organization;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.OrganizationPersistenceEntity;

/**
 * Static assembler between the {@link Organization} aggregate and its JPA persistence representation.
 */
public final class OrganizationPersistenceAssembler {

    private OrganizationPersistenceAssembler() {}

    public static Organization toDomainFromPersistence(OrganizationPersistenceEntity entity) {
        return new Organization(entity.getId(), entity.getName(), entity.getAddress());
    }

    public static OrganizationPersistenceEntity toPersistenceFromDomain(Organization organization) {
        var entity = new OrganizationPersistenceEntity();
        entity.setId(organization.getId() != null ? organization.getId().getValue() : null);
        entity.setName(organization.getName());
        entity.setAddress(organization.getAddress());
        return entity;
    }
}