package com.acme.kampo.platform.field.domain.model.queries;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;


public record GetObservationsByFieldVisitQuery(FieldVisitId fieldVisitId) {
    public GetObservationsByFieldVisitQuery { if (fieldVisitId == null) throw new IllegalArgumentException("fieldVisitId must not be null"); }
}