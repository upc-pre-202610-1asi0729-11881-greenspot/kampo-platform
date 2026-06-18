package com.acme.kampo.platform.profileaccess.domain.model.enums;

/**
 * Represents the position or role type that a user can hold within the platform.
 *
 * <p>Used by {@link com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role}
 * to classify its purpose and by the security layer to enforce access control.</p>
 */
public enum RolePosition {

    /** Full system access — can manage users, roles and all bounded contexts. */
    ADMIN,

    /** Agricultural specialist — can manage field operations and observations. */
    AGRONOMIST,

    /** Field operative — can record observations and field visits. */
    TECHNICIAN,

    /** Oversight role — can view reports and monitor alerts across fundos. */
    SUPERVISOR;

    /** Returns {@code true} if this position grants administrative privileges. */
    public boolean isAdmin() {
        return this == ADMIN;
    }
}