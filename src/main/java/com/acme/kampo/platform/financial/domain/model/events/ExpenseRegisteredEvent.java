package com.acme.kampo.platform.financial.domain.model.events;

import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;

/**
 * Domain event published when a new {@link Expense} is successfully registered.
 * Carries the full aggregate for enriched consumers.
 *
 * @param expense the newly persisted expense aggregate
 */
public record ExpenseRegisteredEvent(Expense expense) {}

