package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.embeddables.GeoLocationPersistenceEmbeddable;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.organization.domain.model.aggregates.Fundo} aggregate.
 * Uses {@link GeoLocationPersistenceEmbeddable} to persist the geographic location.
 */
@Setter
@Getter
@Entity
@Table(name = "fundos")
public class FundoPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "organization_id", nullable = false)
    private Long organizationId;

    @Column(nullable = false)
    private String name;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "latitude",  column = @Column(name = "latitude",  nullable = false)),
            @AttributeOverride(name = "longitude", column = @Column(name = "longitude", nullable = false))
    })
    private GeoLocationPersistenceEmbeddable location;

    public FundoPersistenceEntity() {}

}