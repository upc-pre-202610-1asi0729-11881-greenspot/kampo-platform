package com.acme.kampo.platform.report.domain.model.aggregates;

import com.acme.kampo.platform.report.domain.model.commands.GenerateReportCommand;
import com.acme.kampo.platform.report.domain.model.enums.ReportType;
import com.acme.kampo.platform.report.domain.model.events.ReportGeneratedEvent;
import com.acme.kampo.platform.report.domain.model.valueObjects.FileUrl;
import com.acme.kampo.platform.report.domain.model.valueObjects.ReportId;
import com.acme.kampo.platform.report.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.report.domain.model.valueObjects.UserId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

public class Report extends AbstractDomainAggregateRoot<Report> {

    private ReportId id;
    private ReportType type;
    private FileUrl fileUrl;
    private UserId userId;
    private SeasonId seasonId;

    protected Report() {}

    public Report(GenerateReportCommand command) {
        this.type = command.type();
        this.fileUrl = command.fileUrl();
        this.userId = command.userId();
        this.seasonId = command.seasonId();
        registerDomainEvent(new ReportGeneratedEvent(this));
    }

    public void attachFile(FileUrl fileUrl) {
        if (fileUrl == null)
            throw new IllegalArgumentException("FileUrl must not be null");
        this.fileUrl = fileUrl;
    }

    public static Report reconstitute() {
        return new Report();
    }

    public ReportId getId() { return id; }
    public ReportType getType() { return type; }
    public FileUrl getFileUrl() { return fileUrl; }
    public UserId getUserId() { return userId; }
    public SeasonId getSeasonId() { return seasonId; }

    public void setId(ReportId id) { this.id = id; }
    public void setType(ReportType type) { this.type = type; }
    public void setFileUrl(FileUrl fileUrl) { this.fileUrl = fileUrl; }
    public void setUserId(UserId userId) { this.userId = userId; }
    public void setSeasonId(SeasonId seasonId) { this.seasonId = seasonId; }
}