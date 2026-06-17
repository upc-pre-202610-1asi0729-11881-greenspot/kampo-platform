package com.acme.kampo.platform.organization.domain.model.events;

import com.acme.kampo.platform.organization.domain.model.aggregates.Organization;

/**
 * Domain event published when a new {@link Organization} is successfully created.
 * Carries the full aggregate so event handlers can extract any field without a query.
 *
 * @param organization the newly persisted organization aggregate
 */
public record OrganizationCreatedEvent(Organization organization) {}