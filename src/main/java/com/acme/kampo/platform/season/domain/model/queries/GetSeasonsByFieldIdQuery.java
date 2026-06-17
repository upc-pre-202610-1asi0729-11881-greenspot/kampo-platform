package com.acme.kampo.platform.season.domain.model.queries;

import com.acme.kampo.platform.season.domain.model.valueObjects.FieldId;

public record GetSeasonsByFieldIdQuery(FieldId fieldId) {

    public GetSeasonsByFieldIdQuery {
        if (fieldId == null)
            throw new IllegalArgumentException("FieldId must not be null");
    }
}