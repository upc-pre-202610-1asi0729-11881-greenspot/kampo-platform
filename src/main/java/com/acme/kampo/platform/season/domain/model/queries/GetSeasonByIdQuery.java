package com.acme.kampo.platform.season.domain.model.queries;

import com.acme.kampo.platform.season.domain.model.valueobjects.SeasonId;

public record GetSeasonByIdQuery(SeasonId seasonId) {

    public GetSeasonByIdQuery{
        if (seasonId == null)
            throw new IllegalArgumentException("SeasonId must not be null");
    }
}
