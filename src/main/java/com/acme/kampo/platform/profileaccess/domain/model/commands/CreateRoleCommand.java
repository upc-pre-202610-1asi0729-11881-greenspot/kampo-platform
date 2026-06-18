package com.acme.kampo.platform.profileaccess.domain.model.commands;

import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;

/**
 * Command to create a new role in the system.
 *
 * @param position    the role position — must be unique
 * @param description human-readable description of the role's responsibilities
 */
public record CreateRoleCommand(RolePosition position, String description) {
    public CreateRoleCommand {
        if (position == null)
            throw new IllegalArgumentException("position must not be null");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description must not be blank");
    }
}