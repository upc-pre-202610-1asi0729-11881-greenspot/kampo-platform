package com.acme.kampo.platform.profileaccess.domain.model.aggregates;

import com.acme.kampo.platform.profileaccess.domain.model.commands.ModifyProfileCommand;
import com.acme.kampo.platform.profileaccess.domain.model.commands.RegisterUserCommand;
import com.acme.kampo.platform.profileaccess.domain.model.events.UserRegisteredEvent;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.Email;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.HashedPassword;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Aggregate root representing a registered user of the platform.
 *
 * <p>Responsible for:
 * <ul>
 *   <li>Holding identity ({@link Email}), profile data and credentials ({@link HashedPassword}).</li>
 *   <li>Verifying passwords without exposing the hash.</li>
 *   <li>Publishing {@link UserRegisteredEvent} on creation.</li>
 * </ul>
 *
 * <p>Email cannot be changed after registration — it is the user's immutable identity.</p>
 */
@Getter
public class User extends AbstractDomainAggregateRoot<User> {

    private UserId         id;
    private String         firstName;
    private String         lastName;
    private Email          email;
    private String         phone;
    private HashedPassword passwordHash;

    /** Required by JPA proxy — do not use directly. */
    protected User() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public User(Long id, String firstName, String lastName,
                String email, String phone, String passwordHash) {
        this.id           = UserId.of(id);
        this.firstName    = firstName;
        this.lastName     = lastName;
        this.email        = Email.of(email);
        this.phone        = phone;
        this.passwordHash = HashedPassword.fromHash(passwordHash);
    }

    /**
     * Creation constructor — hashes the raw password and registers a {@link UserRegisteredEvent}.
     *
     * @param command  the registration command carrying profile data and raw password
     * @param encoder  the password encoder used to hash the raw password
     */
    public User(RegisterUserCommand command, PasswordEncoder encoder) {
        this.firstName    = command.firstName();
        this.lastName     = command.lastName();
        this.email        = Email.of(command.email());
        this.phone        = command.phone();
        this.passwordHash = HashedPassword.of(command.rawPassword(), encoder);
        registerDomainEvent(new UserRegisteredEvent(this));
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Updates the user's profile — firstName, lastName and phone.
     * Email is immutable and cannot be changed.
     *
     * @param command the profile modification command
     */
    public void modifyProfile(ModifyProfileCommand command) {
        this.firstName = command.firstName();
        this.lastName  = command.lastName();
        this.phone     = command.phone();
    }

    /**
     * Verifies whether the given raw password matches the stored hash.
     *
     * @param rawPassword the plain-text password to verify
     * @param encoder     the password encoder
     * @return {@code true} if the password matches
     */
    public boolean verifyPassword(String rawPassword, PasswordEncoder encoder) {
        return passwordHash.matches(rawPassword, encoder);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public User reconstitute(Long rawId) {
        this.id = UserId.of(rawId);
        return this;
    }
}