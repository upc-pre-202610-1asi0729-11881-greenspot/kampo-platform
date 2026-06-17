package com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.field.domain.model.enums.Severity;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.fieldoperation.domain.model.aggregates.Observation} aggregate.
 * Translation handled by {@link com.acme.kampo.platform.fieldoperation.infrastructure.persistence.jpa.assemblers.ObservationPersistenceAssembler}.
 */
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

    public Long     getFieldVisitId()                      { return fieldVisitId; }
    public void setFieldVisitId(Long fieldVisitId)         { this.fieldVisitId = fieldVisitId; }
    public String   getNotes()                             { return notes; }
    public void setNotes(String notes)                     { this.notes = notes; }
    public String   getPestName()                          { return pestName; }
    public void setPestName(String pestName)               { this.pestName = pestName; }
    public Severity getPestSeverity()                      { return pestSeverity; }
    public void setPestSeverity(Severity pestSeverity)     { this.pestSeverity = pestSeverity; }
    public String   getDiseaseName()                       { return diseaseName; }
    public void setDiseaseName(String diseaseName)         { this.diseaseName = diseaseName; }
    public Severity getDiseaseSeverity()                   { return diseaseSeverity; }
    public void setDiseaseSeverity(Severity diseaseSeverity){ this.diseaseSeverity = diseaseSeverity; }
    public String   getRecommendation()                    { return recommendation; }
    public void setRecommendation(String recommendation)   { this.recommendation = recommendation; }
    public String   getEvidenceUrl()                       { return evidenceUrl; }
    public void setEvidenceUrl(String evidenceUrl)         { this.evidenceUrl = evidenceUrl; }
}