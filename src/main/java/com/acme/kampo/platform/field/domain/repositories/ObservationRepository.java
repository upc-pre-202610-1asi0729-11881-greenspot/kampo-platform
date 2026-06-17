package com.acme.kampo.platform.field.domain.repositories;

import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;
import com.acme.kampo.platform.field.domain.model.valueobjects.ObservationId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Observation} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface ObservationRepository {

    /**
     * Persists a new Observation aggregate.
     *
     * @param observation the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Observation save(Observation observation);

    /**
     * Finds an Observation by its typed identity.
     *
     * @param id the observation identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Observation> findById(ObservationId id);

    /**
     * Returns all observations registered for a given field visit.
     *
     * @param fieldVisitId the field visit identity
     * @return list of observations, possibly empty
     */
    List<Observation> findByFieldVisitId(FieldVisitId fieldVisitId);

    /**
     * Returns {@code true} if an observation with the given identity exists.
     *
     * @param id the observation identity to check
     */
    boolean existsById(ObservationId id);
}