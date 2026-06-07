package com.acme.kampo.platform.financial.domain.model.events;

import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;

/**
 * Domain event published when a new {@link Sale} is successfully registered.
 *
 * @param sale the newly persisted sale aggregate
 */
public record SaleRegisteredEvent(Sale sale) {}

