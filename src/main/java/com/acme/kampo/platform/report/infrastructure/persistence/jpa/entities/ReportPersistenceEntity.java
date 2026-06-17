package com.acme.kampo.platform.report.infrastructure.persistence.jpa.entities;

import com.acme.kampo.platform.report.domain.model.enums.ReportType;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "reports")
public class ReportPersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportType type;

    @Column
    private String fileUrl;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long seasonId;

    public ReportPersistenceEntity() {}
}