package com.acme.kampo.platform.organization.interfaces.acl;

import java.util.Optional;

/**
 * Anti-Corruption Layer facade exposing the Organization bounded context
 * to other bounded contexts.
 *
 * <p>All methods work with primitive types — never with internal domain
 * aggregates or value objects from this context.</p>
 */
public interface OrganizationsContextFacade {

    /**
     * Creates a new organization and returns its assigned ID.
     *
     * @param name    organization name
     * @param address physical address
     * @return the ID of the newly created organization
     */
    Long createOrganization(String name, String address);

    /**
     * Finds an organization ID by name.
     *
     * @param name the organization name to search
     * @return an {@link Optional} with the organization ID, or empty if not found
     */
    Optional<Long> fetchOrganizationIdByName(String name);
}
