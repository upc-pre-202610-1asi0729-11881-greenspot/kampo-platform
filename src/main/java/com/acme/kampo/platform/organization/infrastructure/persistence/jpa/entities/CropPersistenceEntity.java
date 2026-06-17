package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.organization.domain.model.aggregates.Crop} aggregate.
 */
@Setter
@Getter
@Entity
@Table(name = "crops")
public class CropPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "field_id", nullable = false)
    private Long fieldId;

    @Column(nullable = false)
    private String name;

    @Column(name = "planted_at", nullable = false)
    private LocalDate plantedAt;

    public CropPersistenceEntity() {}

}