package com.acme.kampo.platform.profileaccess.application.queryservices;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Permission;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role;
import com.acme.kampo.platform.profileaccess.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Role and Permission read operations.
 */
public interface RoleQueryService {

    Optional<Role>   handle(GetRoleByIdQuery query);

    List<Role>       handle(GetAllRolesQuery query);

    List<Role>       handle(GetRolesByUserQuery query);

    List<Permission> handle(GetPermissionsByRoleQuery query);
}