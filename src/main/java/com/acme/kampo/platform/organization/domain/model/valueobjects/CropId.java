package com.acme.kampo.platform.organization.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Crop aggregate.
 * Immutable — use {@code CropId.of(...)} to construct instances.
 */
@Embeddable
public record CropId(Long value) {

    public CropId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("CropId value must be positive, got: " + value);
    }

    public static CropId of(Long value) { return new CropId(value); }
    public static CropId of(long value) { return new CropId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "CropId(" + value + ")"; }
}
