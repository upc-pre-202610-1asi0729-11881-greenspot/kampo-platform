package com.acme.kampo.platform.profileaccess.domain.model.commands;

/**
 * Command to update a user's profile information.
 * Email cannot be changed — it is the user's identity.
 *
 * @param userId    ID of the user to update
 * @param firstName updated first name
 * @param lastName  updated last name
 * @param phone     updated phone number
 */
public record ModifyProfileCommand(Long userId, String firstName, String lastName, String phone) {
    public ModifyProfileCommand {
        if (userId == null || userId <= 0)
            throw new IllegalArgumentException("userId must be positive");
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("firstName must not be blank");
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("lastName must not be blank");
        if (phone == null || phone.isBlank())
            throw new IllegalArgumentException("phone must not be blank");
    }
}