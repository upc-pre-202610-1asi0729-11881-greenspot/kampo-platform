package com.acme.kampo.platform.report.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.report.domain.model.aggregates.Report;
import com.acme.kampo.platform.report.domain.model.valueobjects.FileUrl;
import com.acme.kampo.platform.report.domain.model.valueobjects.ReportId;
import com.acme.kampo.platform.report.domain.model.valueobjects.SeasonId;
import com.acme.kampo.platform.report.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.report.infrastructure.persistence.jpa.entities.ReportPersistenceEntity;


public class ReportPersistenceAssembler {

    public static ReportPersistenceEntity toEntity(Report report){
        ReportPersistenceEntity entity = new ReportPersistenceEntity();
        if (report.getId() != null)
            entity.setId(report.getId().getValue());
        entity.setType(report.getType());
        if (report.getFileUrl() != null)
            entity.setFileUrl(report.getFileUrl().getValue());
        entity.setUserId(report.getUserId().getValue());
        entity.setSeasonId(report.getSeasonId().getValue());
        return entity;
    }

    public static Report toDomain(ReportPersistenceEntity entity) {
        Report report = Report.reconstitute();
        report.setId(ReportId.of(entity.getId()));
        report.setType(entity.getType());
        if (entity.getFileUrl() != null)
            report.setFileUrl(FileUrl.of(entity.getFileUrl()));
        report.setUserId(UserId.of(entity.getUserId()));
        report.setSeasonId(SeasonId.of(entity.getSeasonId()));
        return report;
    }

}
