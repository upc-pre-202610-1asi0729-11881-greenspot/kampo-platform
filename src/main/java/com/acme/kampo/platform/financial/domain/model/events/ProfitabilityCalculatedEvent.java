package com.acme.kampo.platform.financial.domain.model.events;

import com.acme.kampo.platform.financial.domain.model.aggregates.Profitability;

/**
 * Domain event published when a {@link Profitability} result is calculated and persisted.
 *
 * @param profitability the newly persisted profitability aggregate
 */
public record ProfitabilityCalculatedEvent(Profitability profitability) {}

