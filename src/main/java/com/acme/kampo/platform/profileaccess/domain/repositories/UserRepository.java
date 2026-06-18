package com.acme.kampo.platform.profileaccess.domain.repositories;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.Email;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link User} aggregate.
 */
public interface UserRepository {

    User save(User user);

    Optional<User> findById(UserId id);

    Optional<User> findByEmail(Email email);

    List<User> findAll();

    boolean existsByEmail(Email email);

    boolean existsById(UserId id);
}