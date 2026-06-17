package com.acme.kampo.platform.season.interfaces.rest.transform;

import com.acme.kampo.platform.season.domain.model.aggregates.Season;
import com.acme.kampo.platform.season.interfaces.rest.resources.SeasonResource;

public class SeasonResourceAssembler {

    public static SeasonResource toResource(Season season) {
        return new SeasonResource(
                season.getId().getValue(),
                season.getFieldId().getValue(),
                season.getCropId() != null ? season.getCropId().getValue() : null,
                season.getCropName(),
                season.getStatus(),
                season.getStartedAt(),
                season.getEndedAt());
    }
}