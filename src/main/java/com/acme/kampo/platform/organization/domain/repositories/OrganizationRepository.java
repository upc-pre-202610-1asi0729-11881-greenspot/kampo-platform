package com.acme.kampo.platform.organization.domain.repositories;

import com.acme.kampo.platform.organization.domain.model.aggregates.Organization;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Organization} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface OrganizationRepository {

    /**
     * Persists a new or updated Organization aggregate.
     *
     * @param organization the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Organization save(Organization organization);

    /**
     * Finds an Organization by its typed identity.
     *
     * @param id the organization identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Organization> findById(OrganizationId id);

    /**
     * Finds an Organization by its name.
     *
     * @param name the organization name to search
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Organization> findByName(String name);

    /**
     * Returns all organizations in the system.
     *
     * @return list of all organizations, possibly empty
     */
    List<Organization> findAll();

    /**
     * Deletes an organization by its typed identity.
     *
     * @param id the identity of the organization to delete
     */
    void delete(OrganizationId id);

    /**
     * Returns {@code true} if an organization with the given name already exists.
     * Used to prevent duplicate organization names.
     *
     * @param name the name to check
     */
    boolean existsByName(String name);

    /**
     * Returns {@code true} if an organization with the given identity exists.
     *
     * @param id the organization identity to check
     */
    boolean existsById(OrganizationId id);
}