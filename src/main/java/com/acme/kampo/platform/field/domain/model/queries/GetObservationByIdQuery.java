package com.acme.kampo.platform.field.domain.model.queries;
import com.acme.kampo.platform.field.domain.model.valueobjects.ObservationId;

public record GetObservationByIdQuery(ObservationId observationId) {
    public GetObservationByIdQuery { if (observationId == null) throw new IllegalArgumentException("observationId must not be null"); }
}