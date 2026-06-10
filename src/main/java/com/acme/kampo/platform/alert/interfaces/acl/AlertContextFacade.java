package com.acme.kampo.platform.alert.interfaces.acl;

import java.util.Optional;

/**
 * Anti-Corruption Layer facade exposing the Alert bounded context
 * to other bounded contexts.
 *
 * <p>All methods work with primitive types — never with internal
 * domain aggregates or value objects from this context.</p>
 */
public interface AlertContextFacade {

    /**
     * Configures a new alert rule and returns its assigned ID.
     *
     * @param readingType       the reading type name (e.g. "TEMPERATURE")
     * @param conditionOperator the operator name (e.g. "GREATER_THAN")
     * @param threshold         the threshold value
     * @param severity          the severity level name (e.g. "HIGH")
     * @param fieldId           the field to monitor
     * @return the ID of the newly created alert rule
     */
    Long createAlertRule(String readingType, String conditionOperator,
                         Double threshold, String severity, Long fieldId);

    /**
     * Finds an alert rule ID by the field it monitors.
     * Returns the first matching rule if multiple exist.
     *
     * @param fieldId the field identity
     * @return an {@link Optional} with the alert rule ID, or empty if none configured
     */
    Optional<Long> fetchAlertRuleIdByFieldId(Long fieldId);
}