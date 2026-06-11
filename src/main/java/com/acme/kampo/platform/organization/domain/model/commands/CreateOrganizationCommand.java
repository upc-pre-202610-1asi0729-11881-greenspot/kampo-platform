package com.acme.kampo.platform.organization.domain.model.commands;

/**
 * Command to create a new organization.
 *
 * @param name    organization name — must be unique
 * @param address physical address of the organization
 */
public record CreateOrganizationCommand(String name, String address) {
    public CreateOrganizationCommand {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name must not be blank");
        if (address == null || address.isBlank())
            throw new IllegalArgumentException("address must not be blank");
    }
}