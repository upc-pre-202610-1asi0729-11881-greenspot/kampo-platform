package com.acme.kampo.platform.field.domain.model.events;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;

/**
 * Domain event published when a {@link FieldVisit} is marked as completed.
 *
 * @param fieldVisit the completed field visit aggregate
 */
public record FieldVisitCompletedEvent(FieldVisit fieldVisit) {}