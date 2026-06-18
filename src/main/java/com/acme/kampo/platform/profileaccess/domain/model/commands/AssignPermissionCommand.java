package com.acme.kampo.platform.profileaccess.domain.model.commands;

/**
 * Command to assign a permission to a role.
 *
 * @param roleId       the ID of the role receiving the permission
 * @param permissionId the ID of the permission to assign
 */
public record AssignPermissionCommand(Long roleId, Long permissionId) {
    public AssignPermissionCommand {
        if (roleId == null || roleId <= 0)
            throw new IllegalArgumentException("roleId must be positive");
        if (permissionId == null || permissionId <= 0)
            throw new IllegalArgumentException("permissionId must be positive");
    }
}