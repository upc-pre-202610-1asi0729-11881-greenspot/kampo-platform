package com.acme.kampo.platform.season.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.season.domain.model.enums.SeasonStatus;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "seasons")
public class SeasonPersistenceEntity extends AuditableAbstractPersistenceEntity {


    @Column(nullable = false)
    private Long fieldId;

    @Column
    private Long cropId;

    @Column(nullable = false)
    private String cropName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private SeasonStatus status;

    @Column(nullable = false)
    private LocalDate startedAt;

    @Column
    private LocalDate endedAt;

    public SeasonPersistenceEntity() {}
}