package com.acme.kampo.platform.field.domain.repositories;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.field.domain.model.valueobjects.FieldVisitId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link FieldVisit} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface FieldVisitRepository {

    /**
     * Persists a new or updated FieldVisit aggregate.
     *
     * @param fieldVisit the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    FieldVisit save(FieldVisit fieldVisit);

    /**
     * Finds a FieldVisit by its typed identity.
     *
     * @param id the field visit identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<FieldVisit> findById(FieldVisitId id);

    /**
     * Returns all field visits for a given field, ordered by scheduledAt descending.
     *
     * @param fieldId the field identity
     * @return list of field visits, possibly empty
     */
    List<FieldVisit> findByFieldId(FieldId fieldId);

    /**
     * Returns {@code true} if a field visit with the given identity exists.
     *
     * @param id the field visit identity to check
     */
    boolean existsById(FieldVisitId id);
}