package com.acme.kampo.platform.season.domain.model.valueObjects;

public record SeasonId(Long value) {
    public SeasonId{
        if (value == null || value <= 0)
            throw new IllegalArgumentException("SeasonId must be a positive number");
    }

    public static SeasonId of(Long value) {
        return new SeasonId(value);
    }

    public Long getValue() {
        return value;
    }
}
