package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role} aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "roles")
public class RolePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private RolePosition position;

    @Column(nullable = false)
    private String description;

    public RolePersistenceEntity() {}

}