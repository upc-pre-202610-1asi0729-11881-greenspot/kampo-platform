package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.organization.domain.model.aggregates.Organization} aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "organizations")
public class OrganizationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private String address;

    public OrganizationPersistenceEntity() {}

}