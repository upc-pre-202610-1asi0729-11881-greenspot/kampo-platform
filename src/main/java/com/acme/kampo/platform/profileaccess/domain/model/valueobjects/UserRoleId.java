package com.acme.kampo.platform.profileaccess.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record UserRoleId(Long value) {

    public UserRoleId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("UserRoleId value must be positive, got: " + value);
    }

    public static UserRoleId of(Long value) { return new UserRoleId(value); }
    public static UserRoleId of(long value) { return new UserRoleId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "UserRoleId(" + value + ")"; }
}