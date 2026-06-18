package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.profileaccess.domain.model.aggregates.UserRole} aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "user_roles",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "role_id"}))
public class UserRolePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "role_id", nullable = false)
    private Long roleId;

    public UserRolePersistenceEntity() {}

}