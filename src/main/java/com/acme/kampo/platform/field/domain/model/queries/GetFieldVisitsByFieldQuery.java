package com.acme.kampo.platform.field.domain.model.queries;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldId;

public record GetFieldVisitsByFieldQuery(FieldId fieldId) {
    public GetFieldVisitsByFieldQuery { if (fieldId == null) throw new IllegalArgumentException("fieldId must not be null"); }
}