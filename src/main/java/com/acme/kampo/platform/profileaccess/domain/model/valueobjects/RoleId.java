package com.acme.kampo.platform.profileaccess.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record RoleId(Long value) {

    public RoleId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("RoleId value must be positive, got: " + value);
    }

    public static RoleId of(Long value) { return new RoleId(value); }
    public static RoleId of(long value) { return new RoleId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "RoleId(" + value + ")"; }
}