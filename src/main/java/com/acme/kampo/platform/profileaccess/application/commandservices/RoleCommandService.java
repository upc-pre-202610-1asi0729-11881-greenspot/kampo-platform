package com.acme.kampo.platform.profileaccess.application.commandservices;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Permission;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.RolePermission;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.UserRole;
import com.acme.kampo.platform.profileaccess.domain.model.commands.*;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for Role, Permission, UserRole and RolePermission write operations.
 */
public interface RoleCommandService {

    Result<Role, ApplicationError>           handle(CreateRoleCommand command);

    Result<Permission, ApplicationError>     handle(CreatePermissionCommand command);

    Result<UserRole, ApplicationError>       handle(AssignRoleCommand command);

    Result<RolePermission, ApplicationError> handle(AssignPermissionCommand command);
}