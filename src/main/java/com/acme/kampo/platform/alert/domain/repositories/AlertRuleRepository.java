package com.acme.kampo.platform.alert.domain.repositories;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link AlertRule} aggregate.
 *
 * <p>Lives in the domain layer — no coupling to JPA, Spring Data, or any
 * infrastructure concern.</p>
 */
public interface AlertRuleRepository {

    /**
     * Persists a new or updated AlertRule aggregate.
     * After saving, the returned instance has its {@link AlertRuleId} populated.
     *
     * @param alertRule the aggregate to persist
     * @return the persisted aggregate with its assigned identity
     */
    AlertRule save(AlertRule alertRule);

    /**
     * Finds an AlertRule by its typed identity.
     *
     * @param id the alert rule identity
     * @return an {@link Optional} containing the aggregate, or empty if not found
     */
    Optional<AlertRule> findById(AlertRuleId id);

    /**
     * Returns all alert rules configured for a given field.
     *
     * @param fieldId the field identity
     * @return list of alert rules for the field, possibly empty
     */
    List<AlertRule> findByFieldId(FieldId fieldId);

    /**
     * Returns all alert rules in the system.
     *
     * @return list of all alert rules, possibly empty
     */
    List<AlertRule> findAll();

    /**
     * Returns {@code true} if an alert rule with the given identity exists.
     *
     * @param id the alert rule identity to check
     */
    boolean existsById(AlertRuleId id);
}