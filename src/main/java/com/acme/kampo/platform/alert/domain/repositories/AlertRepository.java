package com.acme.kampo.platform.alert.domain.repositories;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Alert} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern. The infrastructure adapter implements this interface
 * via dependency inversion.</p>
 */
public interface AlertRepository {

    /**
     * Persists a new or updated Alert aggregate.
     * After saving, the returned instance has its {@link AlertId} populated.
     *
     * @param alert the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    Alert save(Alert alert);

    /**
     * Finds an Alert by its typed identity.
     *
     * @param id the alert identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<Alert> findById(AlertId id);

    /**
     * Returns all alerts registered for a given field.
     *
     * @param fieldId the field identity
     * @return list of alerts for the field, possibly empty
     */
    List<Alert> findByFieldId(FieldId fieldId);

    /**
     * Returns all unread alerts in the system.
     *
     * @return list of unread alerts, possibly empty
     */
    List<Alert> findAllUnread();

    /**
     * Returns all alerts in the system.
     *
     * @return list of all alerts, possibly empty
     */
    List<Alert> findAll();

    /**
     * Returns {@code true} if an alert with the given identity exists.
     *
     * @param id the alert identity to check
     */
    boolean existsById(AlertId id);
}