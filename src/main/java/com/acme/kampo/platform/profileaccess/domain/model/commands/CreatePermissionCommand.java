package com.acme.kampo.platform.profileaccess.domain.model.commands;

import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;

/**
 * Command to create a new permission in the system.
 *
 * @param category    the functional area this permission controls
 * @param description human-readable description of what the permission allows
 */
public record CreatePermissionCommand(PermissionCategory category, String description) {
    public CreatePermissionCommand {
        if (category == null)
            throw new IllegalArgumentException("category must not be null");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description must not be blank");
    }
}