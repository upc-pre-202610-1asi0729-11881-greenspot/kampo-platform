package com.acme.kampo.platform.profileaccess.domain.model.events;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;

/**
 * Domain event published when a new {@link User} is successfully registered.
 * Carries the full aggregate so the event handler can extract any field without an extra query.
 *
 * @param user the newly persisted user aggregate
 */
public record UserRegisteredEvent(User user) {}