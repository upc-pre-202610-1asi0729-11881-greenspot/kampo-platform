package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.RolePersistenceEntity;

/**
 * Static assembler between the {@link Role} aggregate and its JPA persistence representation.
 */
public final class RolePersistenceAssembler {

    private RolePersistenceAssembler() {}

    public static Role toDomainFromPersistence(RolePersistenceEntity entity) {
        return new Role(entity.getId(), entity.getPosition(), entity.getDescription());
    }

    public static RolePersistenceEntity toPersistenceFromDomain(Role role) {
        var entity = new RolePersistenceEntity();
        entity.setId(role.getId() != null ? role.getId().getValue() : null);
        entity.setPosition(role.getPosition());
        entity.setDescription(role.getDescription());
        return entity;
    }
}