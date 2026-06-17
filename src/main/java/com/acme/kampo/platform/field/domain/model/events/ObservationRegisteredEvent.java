package com.acme.kampo.platform.field.domain.model.events;

import com.acme.kampo.platform.field.domain.model.aggregates.Observation;

/**
 * Domain event published when a new {@link Observation} is registered.
 *
 * @param observation the newly created observation aggregate
 */
public record ObservationRegisteredEvent(Observation observation) {}