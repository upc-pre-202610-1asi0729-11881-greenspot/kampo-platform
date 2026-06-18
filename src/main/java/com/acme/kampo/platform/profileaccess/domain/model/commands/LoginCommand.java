package com.acme.kampo.platform.profileaccess.domain.model.commands;

/**
 * Command to authenticate a user and obtain a JWT token.
 *
 * @param email       the user's registered email address
 * @param rawPassword the plain-text password to verify
 */
public record LoginCommand(String email, String rawPassword) {
    public LoginCommand {
        if (email == null || email.isBlank())
            throw new IllegalArgumentException("email must not be blank");
        if (rawPassword == null || rawPassword.isBlank())
            throw new IllegalArgumentException("rawPassword must not be blank");
    }
}