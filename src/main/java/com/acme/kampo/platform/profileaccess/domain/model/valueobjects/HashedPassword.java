package com.acme.kampo.platform.profileaccess.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Value object representing a bcrypt-hashed password.
 *
 * <p>The raw password is never stored — only the hash. Two factory methods
 * are provided:
 * <ul>
 *   <li>{@link #of(String, PasswordEncoder)} — hashes a raw password on creation.</li>
 *   <li>{@link #fromHash(String)} — reconstitutes from a stored hash (used by assemblers).</li>
 * </ul>
 *
 * <p>Verification is done via {@link #matches(String, PasswordEncoder)} which delegates
 * to Spring Security's {@link PasswordEncoder#matches(CharSequence, String)}.</p>
 *
 * @param hash the bcrypt hash — never the raw password
 */
@Embeddable
public record HashedPassword(String hash) {

    public HashedPassword {
        if (hash == null || hash.isBlank())
            throw new IllegalArgumentException("password hash must not be blank");
    }

    /**
     * Hashes a raw password using the provided encoder.
     *
     * @param rawPassword the plain-text password to hash
     * @param encoder     the password encoder (e.g. BCryptPasswordEncoder)
     * @return a new {@link HashedPassword} containing the hash
     */
    public static HashedPassword of(String rawPassword, PasswordEncoder encoder) {
        if (rawPassword == null || rawPassword.isBlank())
            throw new IllegalArgumentException("raw password must not be blank");
        if (rawPassword.length() < 8)
            throw new IllegalArgumentException("password must be at least 8 characters");
        return new HashedPassword(encoder.encode(rawPassword));
    }

    /**
     * Reconstitutes a {@link HashedPassword} from a stored hash.
     * Used exclusively by the persistence assembler.
     *
     * @param hash the bcrypt hash from the database
     * @return a reconstituted {@link HashedPassword}
     */
    public static HashedPassword fromHash(String hash) {
        return new HashedPassword(hash);
    }

    /**
     * Verifies whether the given raw password matches this hash.
     *
     * @param rawPassword the plain-text password to verify
     * @param encoder     the password encoder
     * @return {@code true} if the password matches the hash
     */
    public boolean matches(String rawPassword, PasswordEncoder encoder) {
        return encoder.matches(rawPassword, this.hash);
    }

    public String getHash() { return hash; }

    @Override
    public String toString() { return "[PROTECTED]"; }
}