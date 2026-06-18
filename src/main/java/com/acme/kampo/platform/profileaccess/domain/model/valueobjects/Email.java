package com.acme.kampo.platform.profileaccess.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.regex.Pattern;

/**
 * Value object representing a validated email address.
 *
 * <p>Immutable — use {@code Email.of(...)} to construct instances.
 * Validation is enforced on construction: a malformed email is rejected
 * with {@link IllegalArgumentException}.</p>
 *
 * @param value the validated email address in lowercase
 */
@Embeddable
public record Email(String value) {

    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            "^[a-zA-Z0-9._%+\\-]+@[a-zA-Z0-9.\\-]+\\.[a-zA-Z]{2,}$");

    public Email {
        if (value == null || value.isBlank())
            throw new IllegalArgumentException("email must not be blank");
        if (!EMAIL_PATTERN.matcher(value).matches())
            throw new IllegalArgumentException("invalid email format: " + value);
        value = value.toLowerCase();
    }

    /**
     * Factory method — constructs and validates an {@link Email}.
     *
     * @param value raw email string
     * @return a validated {@link Email} instance
     * @throws IllegalArgumentException if the format is invalid
     */
    public static Email of(String value) {
        return new Email(value);
    }

    /**
     * Returns {@code true} if the given string is a valid email format.
     * Useful for pre-validation before construction.
     */
    public static boolean isValid(String value) {
        return value != null && EMAIL_PATTERN.matcher(value).matches();
    }

    public String getValue() { return value; }

    @Override
    public String toString() { return value; }
}