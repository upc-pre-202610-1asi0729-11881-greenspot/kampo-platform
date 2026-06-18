package com.acme.kampo.platform.profileaccess.interfaces.rest.transform;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.*;
import com.acme.kampo.platform.profileaccess.domain.model.commands.*;
import com.acme.kampo.platform.profileaccess.interfaces.rest.resources.*;

/**
 * Static assembler for all resource ↔ command/domain translations in the profileaccess context.
 */
public final class ProfileAccessResourceAssembler {

    private ProfileAccessResourceAssembler() {}

    // ── User ──────────────────────────────────────────────────────────────────

    public static RegisterUserCommand toCommand(CreateUserResource r) {
        return new RegisterUserCommand(r.firstName(), r.lastName(), r.email(), r.phone(), r.password());
    }

    public static ModifyProfileCommand toCommand(Long userId, UpdateUserResource r) {
        return new ModifyProfileCommand(userId, r.firstName(), r.lastName(), r.phone());
    }

    public static UserResource toResource(User user) {
        return new UserResource(
                user.getId().getValue(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail().getValue(),
                user.getPhone());
    }

    // ── Auth ──────────────────────────────────────────────────────────────────

    public static LoginCommand toCommand(LoginResource r) {
        return new LoginCommand(r.email(), r.password());
    }

    // ── Role ──────────────────────────────────────────────────────────────────

    public static CreateRoleCommand toCommand(CreateRoleResource r) {
        return new CreateRoleCommand(r.position(), r.description());
    }

    public static AssignRoleCommand toCommand(AssignRoleResource r) {
        return new AssignRoleCommand(r.userId(), r.roleId());
    }

    public static RoleResource toResource(Role role) {
        return new RoleResource(role.getId().getValue(), role.getPosition(), role.getDescription());
    }

    // ── Permission ────────────────────────────────────────────────────────────

    public static CreatePermissionCommand toCommand(CreatePermissionResource r) {
        return new CreatePermissionCommand(r.category(), r.description());
    }

    public static AssignPermissionCommand toCommand(AssignPermissionResource r) {
        return new AssignPermissionCommand(r.roleId(), r.permissionId());
    }

    public static PermissionResource toResource(Permission permission) {
        return new PermissionResource(
                permission.getId().getValue(),
                permission.getCategory(),
                permission.getDescription());
    }
}