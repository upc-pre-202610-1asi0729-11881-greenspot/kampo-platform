package com.acme.kampo.platform.report.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.report.domain.model.enums.PriorityLevel;
import com.acme.kampo.platform.report.domain.model.enums.RecommendationStatus;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "recommendations")
public class RecommendationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PriorityLevel priority;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RecommendationStatus status;

    @Column
    private java.util.Date implementedAt;

    @Column(nullable = false)
    private Long reportId;

    public RecommendationPersistenceEntity() {}
}