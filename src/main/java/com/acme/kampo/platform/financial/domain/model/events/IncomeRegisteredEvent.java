package com.acme.kampo.platform.financial.domain.model.events;


import com.acme.kampo.platform.financial.domain.model.aggregates.Income;

/**
 * Domain event published when a new {@link Income} entry is successfully registered.
 *
 * @param income the newly persisted income aggregate
 */
public record IncomeRegisteredEvent(Income income) {}
