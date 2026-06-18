package com.acme.kampo.platform.season.domain.model.command;

import com.acme.kampo.platform.season.domain.model.enums.SeasonStatus;
import com.acme.kampo.platform.season.domain.model.valueobjects.SeasonId;

public record UpdateSeasonStatusCommand(SeasonId seasonId, SeasonStatus newStatus) {

    public UpdateSeasonStatusCommand {
        if (seasonId == null)
            throw new IllegalArgumentException("SeasonId must not be null");
        if (newStatus == null)
            throw new IllegalArgumentException("New status must not be null");
    }
}