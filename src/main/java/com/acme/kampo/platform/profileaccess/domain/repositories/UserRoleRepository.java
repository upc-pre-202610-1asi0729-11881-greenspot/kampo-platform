package com.acme.kampo.platform.profileaccess.domain.repositories;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.UserRole;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;

import java.util.List;

/**
 * Domain repository contract for the {@link UserRole} aggregate.
 */
public interface UserRoleRepository {

    UserRole save(UserRole userRole);

    List<UserRole> findByUserId(UserId userId);

    List<UserRole> findByRoleId(RoleId roleId);

    boolean existsByUserIdAndRoleId(UserId userId, RoleId roleId);
}