package com.acme.kampo.platform.profileaccess.domain.model.commands;

/**
 * Command to assign a role to a user.
 *
 * @param userId the ID of the user receiving the role
 * @param roleId the ID of the role to assign
 */
public record AssignRoleCommand(Long userId, Long roleId) {
    public AssignRoleCommand {
        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("userId must be positive");
        if (roleId == null || roleId <= 0)
            throw new IllegalArgumentException("roleId must be positive");
    }
}