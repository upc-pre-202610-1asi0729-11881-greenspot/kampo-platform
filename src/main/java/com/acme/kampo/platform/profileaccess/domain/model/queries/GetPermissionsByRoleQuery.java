package com.acme.kampo.platform.profileaccess.domain.model.queries;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
public record GetPermissionsByRoleQuery(RoleId roleId) {
    public GetPermissionsByRoleQuery { if (roleId == null) throw new IllegalArgumentException("roleId must not be null"); }
}