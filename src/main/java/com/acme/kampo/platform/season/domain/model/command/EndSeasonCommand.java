package com.acme.kampo.platform.season.domain.model.command;

import com.acme.kampo.platform.season.domain.model.valueObjects.SeasonId;
import java.time.LocalDate;

public record EndSeasonCommand(SeasonId seasonId, LocalDate endAt) {

    public EndSeasonCommand {
        if (seasonId == null)
            throw new IllegalArgumentException("SeasonId must not be null");
        if (endAt == null)
            throw new IllegalArgumentException("End date must not be null");
    }
}