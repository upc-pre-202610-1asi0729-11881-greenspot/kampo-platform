package com.acme.kampo.platform.field.domain.model.queries;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;

public record GetFieldVisitByIdQuery(FieldVisitId fieldVisitId) {
    public GetFieldVisitByIdQuery { if (fieldVisitId == null) throw new IllegalArgumentException("fieldVisitId must not be null"); }
}