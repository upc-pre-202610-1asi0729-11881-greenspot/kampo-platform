package com.acme.kampo.platform.report.domain.model.aggregates;

import com.acme.kampo.platform.report.domain.model.commands.GenerateRecommendationCommand;
import com.acme.kampo.platform.report.domain.model.commands.ImplementRecommendationCommand;
import com.acme.kampo.platform.report.domain.model.enums.PriorityLevel;
import com.acme.kampo.platform.report.domain.model.enums.RecommendationStatus;
import com.acme.kampo.platform.report.domain.model.valueObjects.RecommendationId;
import com.acme.kampo.platform.report.domain.model.valueObjects.ReportId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;

public class Recommendation extends AbstractDomainAggregateRoot<Recommendation> {

    private RecommendationId id;
    private PriorityLevel priority;
    private RecommendationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime implementedAt;
    private ReportId reportId;

    protected Recommendation(){}

    public Recommendation(GenerateRecommendationCommand command){
        this.priority = command.priority();
        this.status = RecommendationStatus.PENDING;
        this.createdAt = LocalDateTime.now();
        this.reportId = command.reportId();
    }

    public void implement(ImplementRecommendationCommand command){
        if(this.status == RecommendationStatus.IMPLEMENTED)
            throw new IllegalArgumentException("Recommendation is already implemented");
        this.status = RecommendationStatus.IMPLEMENTED;
        this.implementedAt = LocalDateTime.now();
    }

    public static Recommendation reconstitute(){
        return new Recommendation();
    }

    public RecommendationId getId() { return id; }
    public PriorityLevel getPriority() { return priority; }
    public RecommendationStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getImplementedAt() { return implementedAt; }
    public ReportId getReportId() { return reportId; }

    public void setId(RecommendationId id) { this.id = id; }
    public void setPriority(PriorityLevel priority) { this.priority = priority; }
    public void setStatus(RecommendationStatus status) { this.status = status; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public void setImplementedAt(LocalDateTime implementedAt) { this.implementedAt = implementedAt; }
    public void setReportId(ReportId reportId) { this.reportId = reportId; }

}
