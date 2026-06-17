package com.acme.kampo.platform.season.domain.model.aggregates;

import com.acme.kampo.platform.season.domain.model.command.CreateSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.AssignCropToSeasonCommand;
import com.acme.kampo.platform.season.domain.model.command.UpdateSeasonStatusCommand;
import com.acme.kampo.platform.season.domain.model.command.EndSeasonCommand;
import com.acme.kampo.platform.season.domain.model.enums.SeasonStatus;
import com.acme.kampo.platform.season.domain.model.events.SeasonCreatedEvent;
import com.acme.kampo.platform.season.domain.model.valueObjects.CropId;
import com.acme.kampo.platform.season.domain.model.valueObjects.DateRange;
import com.acme.kampo.platform.season.domain.model.valueObjects.FieldId;
import com.acme.kampo.platform.season.domain.model.valueObjects.SeasonId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

public class Season extends AbstractDomainAggregateRoot<Season> {

    private SeasonId id;
    private FieldId fieldId;
    private CropId cropId;
    private String cropName;
    private SeasonStatus status;
    private DateRange dateRange;

    protected Season() {}

    public Season(CreateSeasonCommand command) {
        this.fieldId = command.fieldId();
        this.cropName = command.cropName();
        this.status = SeasonStatus.PLANTING;
        this.dateRange = DateRange.of(command.startAt(), null);
        registerDomainEvent(new SeasonCreatedEvent(this));
    }

    public void assignCrop(AssignCropToSeasonCommand command) {
        this.cropId = command.cropId();
    }

    public void updateStatus(UpdateSeasonStatusCommand command) {
        if (!canTransitionTo(command.newStatus()))
            throw new IllegalArgumentException("Invalid status transition from " + this.status + " to " + command.newStatus());
        this.status = command.newStatus();
    }

    public void end(EndSeasonCommand command) {
        if (this.status == SeasonStatus.ENDED)
            throw new IllegalArgumentException("Season is already ended");
        this.status = SeasonStatus.ENDED;
        this.dateRange = DateRange.of(this.dateRange.startAt(), command.endAt());
    }

    public boolean canTransitionTo(SeasonStatus newStatus) {
        return switch (this.status) {
            case PLANTING -> newStatus == SeasonStatus.GROWING;
            case GROWING -> newStatus == SeasonStatus.HARVESTING;
            case HARVESTING -> newStatus == SeasonStatus.ENDED;
            case ENDED -> false;
        };
    }

    public static Season reconstitute(){
        return new Season();
    }

    public boolean isActive() {
        return this.status != SeasonStatus.ENDED;
    }

    public SeasonId getId() { return id; }
    public FieldId getFieldId() { return fieldId; }
    public CropId getCropId() { return cropId; }
    public String getCropName() { return cropName; }
    public SeasonStatus getStatus() { return status; }
    public DateRange getDateRange() { return dateRange; }
    public java.time.LocalDate getStartedAt() { return dateRange.startAt(); }
    public java.time.LocalDate getEndedAt() { return dateRange.endAt(); }

    public void setId(SeasonId id) { this.id = id; }
    public void setFieldId(FieldId fieldId) { this.fieldId = fieldId; }
    public void setCropId(CropId cropId) { this.cropId = cropId; }
    public void setCropName(String cropName) { this.cropName = cropName; }
    public void setStatus(SeasonStatus status) { this.status = status; }
    public void setDateRange(DateRange dateRange) { this.dateRange = dateRange; }

}