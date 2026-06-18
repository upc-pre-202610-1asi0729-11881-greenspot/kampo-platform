package com.acme.kampo.platform.profileaccess.interfaces.events;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;

/**
 * Integration event published by the {@code profileaccess} bounded context
 * when a new {@link User} is successfully registered.
 *
 * @param userId    the ID of the newly registered user
 * @param email     the user's email address
 * @param firstName the user's first name
 * @param lastName  the user's last name
 */
public record UserRegisteredIntegrationEvent(
        Long userId,
        String email,
        String firstName,
        String lastName) {

    public static UserRegisteredIntegrationEvent from(User user) {
        return new UserRegisteredIntegrationEvent(
                user.getId().getValue(),
                user.getEmail().getValue(),
                user.getFirstName(),
                user.getLastName());
    }
}