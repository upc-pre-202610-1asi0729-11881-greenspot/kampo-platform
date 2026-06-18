package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.UserRole;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.UserRolePersistenceEntity;

/**
 * Static assembler between the {@link UserRole} aggregate and its JPA persistence representation.
 */
public final class UserRolePersistenceAssembler {

    private UserRolePersistenceAssembler() {}

    public static UserRole toDomainFromPersistence(UserRolePersistenceEntity entity) {
        return new UserRole(entity.getId(), entity.getUserId(), entity.getRoleId());
    }

    public static UserRolePersistenceEntity toPersistenceFromDomain(UserRole userRole) {
        var entity = new UserRolePersistenceEntity();
        entity.setId(userRole.getId() != null ? userRole.getId().getValue() : null);
        entity.setUserId(userRole.getUserId().getValue());
        entity.setRoleId(userRole.getRoleId().getValue());
        return entity;
    }
}