package com.acme.kampo.platform.season.domain.model.command;

import com.acme.kampo.platform.season.domain.model.valueobjects.CropId;
import com.acme.kampo.platform.season.domain.model.valueobjects.SeasonId;

public record AssignCropToSeasonCommand(SeasonId seasonId, CropId cropId) {

    public AssignCropToSeasonCommand{
        if(seasonId == null)
            throw new IllegalArgumentException("SeasonId must not be null");
        if(cropId == null)
            throw new IllegalArgumentException("CropId must not be null");
    }

}
