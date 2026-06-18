package com.acme.kampo.platform.profileaccess.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record PermissionId(Long value) {

    public PermissionId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("PermissionId value must be positive, got: " + value);
    }

    public static PermissionId of(Long value) { return new PermissionId(value); }
    public static PermissionId of(long value) { return new PermissionId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "PermissionId(" + value + ")"; }
}