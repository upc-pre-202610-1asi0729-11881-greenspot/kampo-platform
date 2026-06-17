package com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.field.domain.model.enums.Severity;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.fieldoperation.domain.model.aggregates.Observation} aggregate.
 * Translation handled by {@link com.acme.kampo.platform.fieldoperation.infrastructure.persistence.jpa.assemblers.ObservationPersistenceAssembler}.
 */
@Setter
@Getter
@Entity
@Table(name = "observations")
public class ObservationPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "field_visit_id", nullable = false)
    private Long fieldVisitId;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String notes;

    @Column(name = "pest_name")
    private String pestName;

    @Enumerated(EnumType.STRING)
    @Column(name = "pest_severity", length = 10)
    private Severity pestSeverity;

    @Column(name = "disease_name")
    private String diseaseName;

    @Enumerated(EnumType.STRING)
    @Column(name = "disease_severity", length = 10)
    private Severity diseaseSeverity;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String recommendation;

    @Column(name = "evidence_url")
    private String evidenceUrl;

    public ObservationPersistenceEntity() {}

}