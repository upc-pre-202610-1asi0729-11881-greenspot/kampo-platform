package com.acme.kampo.platform.profileaccess.interfaces.acl;

import java.util.List;
import java.util.Optional;

/**
 * Anti-Corruption Layer facade exposing the ProfileAccess bounded context
 * to other bounded contexts.
 *
 * <p>All methods work with primitive types — never with internal domain
 * aggregates or value objects from this context.</p>
 */
public interface ProfileAccessContextFacade {

    /**
     * Finds a user ID by their email address.
     *
     * @param email the user's email
     * @return an {@link Optional} with the user ID, or empty if not found
     */
    Optional<Long> fetchUserIdByEmail(String email);

    /**
     * Returns the list of role position names assigned to a user.
     * Example: ["AGRONOMIST", "SUPERVISOR"]
     *
     * @param userId the user's ID
     * @return list of role position strings, possibly empty
     */
    List<String> fetchRolesByUserId(Long userId);
}