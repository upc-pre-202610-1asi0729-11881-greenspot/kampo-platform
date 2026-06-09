package com.acme.kampo.platform.alert.domain.model.events;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;

/**
 * Domain event published when a new Alert is successfully sent.
 *
 * @param alert the newly persisted alert aggregate
 */
public record AlertSentEvent(Alert alert) {}