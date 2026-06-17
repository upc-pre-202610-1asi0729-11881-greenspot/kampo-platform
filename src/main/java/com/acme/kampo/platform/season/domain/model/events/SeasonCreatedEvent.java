package com.acme.kampo.platform.season.domain.model.events;

import com.acme.kampo.platform.season.domain.model.aggregates.Season;

public record SeasonCreatedEvent(Season season) {

    public Long getSeasonId() {
        return season.getId() != null ? season.getId().getValue() : null;
    }
}