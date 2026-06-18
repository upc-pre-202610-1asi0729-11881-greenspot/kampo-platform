package com.acme.kampo.platform.profileaccess.domain.model.queries;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
public record GetRolesByUserQuery(UserId userId) {
    public GetRolesByUserQuery { if (userId == null) throw new IllegalArgumentException("userId must not be null"); }
}