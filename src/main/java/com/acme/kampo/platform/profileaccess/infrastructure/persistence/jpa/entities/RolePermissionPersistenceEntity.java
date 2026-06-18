package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.profileaccess.domain.model.aggregates.RolePermission} aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "role_permissions",
        uniqueConstraints = @UniqueConstraint(columnNames = {"role_id", "permission_id"}))
public class RolePermissionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    @Column(name = "permission_id", nullable = false)
    private Long permissionId;

    public RolePermissionPersistenceEntity() {}

}