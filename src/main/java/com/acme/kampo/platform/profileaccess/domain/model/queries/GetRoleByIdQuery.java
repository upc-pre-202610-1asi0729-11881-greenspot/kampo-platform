package com.acme.kampo.platform.profileaccess.domain.model.queries;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
public record GetRoleByIdQuery(RoleId roleId) {
    public GetRoleByIdQuery { if (roleId == null) throw new IllegalArgumentException("roleId must not be null"); }
}