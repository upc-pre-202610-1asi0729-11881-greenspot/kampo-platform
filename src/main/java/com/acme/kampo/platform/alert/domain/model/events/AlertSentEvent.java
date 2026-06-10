package com.acme.kampo.platform.alert.domain.model.events;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;

/**
 * Domain event published when a new {@link Alert} is successfully sent (created).
 * Carries the full aggregate so the event handler can extract any field it needs
 * without an extra query.
 *
 * @param alert the newly persisted alert aggregate
 */
public record AlertSentEvent(Alert alert) {}