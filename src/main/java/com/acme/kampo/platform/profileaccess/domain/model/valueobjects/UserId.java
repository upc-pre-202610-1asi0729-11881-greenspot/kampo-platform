package com.acme.kampo.platform.profileaccess.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserId(Long value) {

    public UserId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("UserId value must be positive, got: " + value);
    }

    public static UserId of(Long value) { return new UserId(value); }
    public static UserId of(long value) { return new UserId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "UserId(" + value + ")"; }
}