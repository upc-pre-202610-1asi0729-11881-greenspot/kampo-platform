package com.acme.kampo.platform.organization.domain.model.commands;

/**
 * Command to delete an organization by its ID.
 *
 * @param organizationId ID of the organization to delete
 */
public record DeleteOrganizationCommand(Long organizationId) {
    public DeleteOrganizationCommand {
        if (organizationId == null || organizationId <= 0)
            throw new IllegalArgumentException("organizationId must be positive");
    }
}
