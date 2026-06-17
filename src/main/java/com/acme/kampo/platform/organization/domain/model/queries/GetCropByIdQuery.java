package com.acme.kampo.platform.organization.domain.model.queries;
import com.acme.kampo.platform.organization.domain.model.valueobjects.CropId;
public record GetCropByIdQuery(CropId cropId) {
    public GetCropByIdQuery { if (cropId == null) throw new IllegalArgumentException("cropId must not be null"); }
}
