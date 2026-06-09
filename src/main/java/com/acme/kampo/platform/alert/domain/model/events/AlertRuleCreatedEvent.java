package com.acme.kampo.platform.alert.domain.model.events;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;

/**
 * Domain event published when a new AlertRule is successfully created.
 *
 * @param alertRule the newly persisted alert rule aggregate
 */
public record AlertRuleCreatedEvent(AlertRule alertRule) {}