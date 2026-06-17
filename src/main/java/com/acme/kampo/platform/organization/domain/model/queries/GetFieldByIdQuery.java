package com.acme.kampo.platform.organization.domain.model.queries;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId;
public record GetFieldByIdQuery(FieldId fieldId) {
    public GetFieldByIdQuery { if (fieldId == null) throw new IllegalArgumentException("fieldId must not be null"); }
}
