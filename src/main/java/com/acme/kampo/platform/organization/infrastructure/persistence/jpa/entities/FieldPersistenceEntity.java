package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.organization.domain.model.aggregates.Field} aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "fields")
public class FieldPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "fundo_id", nullable = false)
    private Long fundoId;

    @Column(nullable = false)
    private String name;

    @Column(name = "area_sqm", nullable = false)
    private Double areaSqm;

    public FieldPersistenceEntity() {}

}