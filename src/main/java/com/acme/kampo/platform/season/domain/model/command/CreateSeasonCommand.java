package com.acme.kampo.platform.season.domain.model.command;

import com.acme.kampo.platform.season.domain.model.valueobjects.FieldId;
import java.time.LocalDate;
public record CreateSeasonCommand(FieldId fieldId, String cropName, LocalDate startAt) {

    public CreateSeasonCommand{
        if(fieldId == null)
            throw new IllegalArgumentException("FieldId must not be null");
        if(cropName == null || cropName.isBlank())
            throw new IllegalArgumentException("Crop name must not be blank");
        if(startAt == null)
            throw new IllegalArgumentException("Start date must not be null");
    }
}
