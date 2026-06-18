package com.acme.kampo.platform.profileaccess.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record RolePermissionId(Long value) {

    public RolePermissionId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("RolePermissionId value must be positive, got: " + value);
    }

    public static RolePermissionId of(Long value) { return new RolePermissionId(value); }
    public static RolePermissionId of(long value) { return new RolePermissionId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "RolePermissionId(" + value + ")"; }
}