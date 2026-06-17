package com.acme.kampo.platform.organization.domain.repositories;

import com.acme.kampo.platform.organization.domain.model.aggregates.Fundo;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FundoId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Fundo} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface FundoRepository {

    /**
     * Persists a new Fundo aggregate.
     *
     * @param fundo the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Fundo save(Fundo fundo);

    /**
     * Finds a Fundo by its typed identity.
     *
     * @param id the fundo identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Fundo> findById(FundoId id);

    /**
     * Returns all fundos registered under a given organization.
     *
     * @param organizationId the organization identity
     * @return list of fundos for the organization, possibly empty
     */
    List<Fundo> findByOrganizationId(OrganizationId organizationId);

    /**
     * Returns all fundos in the system.
     *
     * @return list of all fundos, possibly empty
     */
    List<Fundo> findAll();

    /**
     * Returns {@code true} if a fundo with the given identity exists.
     *
     * @param id the fundo identity to check
     */
    boolean existsById(FundoId id);
}