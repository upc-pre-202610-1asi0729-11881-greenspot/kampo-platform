package com.acme.kampo.platform.organization.domain.repositories;

import com.acme.kampo.platform.organization.domain.model.aggregates.Crop;
import com.acme.kampo.platform.organization.domain.model.valueobjects.CropId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Crop} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface CropRepository {

    /**
     * Persists a new Crop aggregate.
     *
     * @param crop the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Crop save(Crop crop);

    /**
     * Finds a Crop by its typed identity.
     *
     * @param id the crop identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Crop> findById(CropId id);

    /**
     * Returns all crops planted in a given field.
     *
     * @param fieldId the field identity
     * @return list of crops for the field, possibly empty
     */
    List<Crop> findByFieldId(FieldId fieldId);

    /**
     * Returns {@code true} if a crop with the given identity exists.
     *
     * @param id the crop identity to check
     */
    boolean existsById(CropId id);
}
