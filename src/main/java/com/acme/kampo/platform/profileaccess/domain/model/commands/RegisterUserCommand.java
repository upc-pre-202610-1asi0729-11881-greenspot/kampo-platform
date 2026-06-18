package com.acme.kampo.platform.profileaccess.domain.model.commands;

/**
 * Command to register a new user.
 *
 * @param firstName   user's first name
 * @param lastName    user's last name
 * @param email       user's email address — must be unique
 * @param phone       user's phone number
 * @param rawPassword plain-text password — will be hashed by the command service
 */
public record RegisterUserCommand(
        String firstName,
        String lastName,
        String email,
        String phone,
        String rawPassword
) {
    public RegisterUserCommand {
        if (firstName == null || firstName.isBlank())
            throw new IllegalArgumentException("firstName must not be blank");
        if (lastName == null || lastName.isBlank())
            throw new IllegalArgumentException("lastName must not be blank");
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("email must not be blank");
        if (phone == null || phone.isBlank())
            throw new IllegalArgumentException("phone must not be blank");
        if (rawPassword == null || rawPassword.isBlank())
            throw new IllegalArgumentException("rawPassword must not be blank");
    }
}