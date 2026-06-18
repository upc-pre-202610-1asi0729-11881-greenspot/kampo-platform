package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.profileaccess.domain.model.aggregates.Permission} aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "permissions")
public class PermissionPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true, length = 20)
    private PermissionCategory category;

    @Column(nullable = false)
    private String description;

    public PermissionPersistenceEntity() {}

}