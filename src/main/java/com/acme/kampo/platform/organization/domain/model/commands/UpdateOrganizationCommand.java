package com.acme.kampo.platform.organization.domain.model.commands;

/**
 * Command to update the mutable fields of an existing organization.
 *
 * @param organizationId ID of the organization to update
 * @param name           updated name
 * @param address        updated address
 */
public record UpdateOrganizationCommand(Long organizationId, String name, String address) {
    public UpdateOrganizationCommand {
        if (organizationId == null || organizationId <= 0)
            throw new IllegalArgumentException("organizationId must be positive");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name must not be blank");
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("address must not be blank");
    }
}