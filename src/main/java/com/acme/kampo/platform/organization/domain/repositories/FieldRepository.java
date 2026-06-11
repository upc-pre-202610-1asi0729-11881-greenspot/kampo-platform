package com.acme.kampo.platform.organization.domain.repositories;

import com.acme.kampo.platform.organization.domain.model.aggregates.Field;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FundoId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Field} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface FieldRepository {

    /**
     * Persists a new Field aggregate.
     *
     * @param field the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Field save(Field field);

    /**
     * Finds a Field by its typed identity.
     *
     * @param id the field identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Field> findById(FieldId id);

    /**
     * Returns all fields registered within a given fundo.
     *
     * @param fundoId the fundo identity
     * @return list of fields for the fundo, possibly empty
     */
    List<Field> findByFundoId(FundoId fundoId);

    /**
     * Returns {@code true} if a field with the given identity exists.
     *
     * @param id the field identity to check
     */
    boolean existsById(FieldId id);
}