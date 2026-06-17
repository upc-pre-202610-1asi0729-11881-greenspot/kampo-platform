package com.acme.kampo.platform.season.domain.model.queries;

import com.acme.kampo.platform.season.domain.model.valueObjects.FieldId;

public record GetActiveSeasonByFieldIdQuery(FieldId fieldId) {

    public GetActiveSeasonByFieldIdQuery {
        if (fieldId == null)
            throw new IllegalArgumentException("FieldId must not be null");
    }
}