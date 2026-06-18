package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.RolePermission;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.RolePermissionPersistenceEntity;

/**
 * Static assembler between the {@link RolePermission} aggregate and its JPA persistence representation.
 */
public final class RolePermissionPersistenceAssembler {

    private RolePermissionPersistenceAssembler() {}

    public static RolePermission toDomainFromPersistence(RolePermissionPersistenceEntity entity) {
        return new RolePermission(entity.getId(), entity.getRoleId(), entity.getPermissionId());
    }

    public static RolePermissionPersistenceEntity toPersistenceFromDomain(RolePermission rp) {
        var entity = new RolePermissionPersistenceEntity();
        entity.setId(rp.getId() != null ? rp.getId().getValue() : null);
        entity.setRoleId(rp.getRoleId().getValue());
        entity.setPermissionId(rp.getPermissionId().getValue());
        return entity;
    }
}