package com.acme.kampo.platform.profileaccess.domain.repositories;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.RolePermission;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;

import java.util.List;

/**
 * Domain repository contract for the {@link RolePermission} aggregate.
 */
public interface RolePermissionRepository {

    RolePermission save(RolePermission rolePermission);

    List<RolePermission> findByRoleId(RoleId roleId);

    boolean existsByRoleIdAndPermissionId(RoleId roleId, PermissionId permissionId);
}