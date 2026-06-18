package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Permission;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.PermissionPersistenceEntity;

/**
 * Static assembler between the {@link Permission} aggregate and its JPA persistence representation.
 */
public final class PermissionPersistenceAssembler {

    private PermissionPersistenceAssembler() {}

    public static Permission toDomainFromPersistence(PermissionPersistenceEntity entity) {
        return new Permission(entity.getId(), entity.getCategory(), entity.getDescription());
    }

    public static PermissionPersistenceEntity toPersistenceFromDomain(Permission permission) {
        var entity = new PermissionPersistenceEntity();
        entity.setId(permission.getId() != null ? permission.getId().getValue() : null);
        entity.setCategory(permission.getCategory());
        entity.setDescription(permission.getDescription());
        return entity;
    }
}