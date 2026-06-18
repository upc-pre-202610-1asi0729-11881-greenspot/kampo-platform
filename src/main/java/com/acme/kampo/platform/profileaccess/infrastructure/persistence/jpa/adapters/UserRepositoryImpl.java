package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.Email;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.profileaccess.domain.repositories.UserRepository;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers.UserPersistenceAssembler;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories.UserJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link UserRepository} contract using Spring Data JPA.
 */
@Repository
public class UserRepositoryImpl implements UserRepository {

    private final UserJpaRepository         jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public UserRepositoryImpl(UserJpaRepository jpaRepository,
                              ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public User save(User user) {
        boolean isNew = user.getId() == null;
        var entity    = UserPersistenceAssembler.toPersistenceFromDomain(user);
        var saved     = jpaRepository.save(entity);
        var domain    = UserPersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            domain.domainEvents().forEach(eventPublisher::publishEvent);
            domain.clearDomainEvents();
        }
        return domain;
    }

    @Override
    public Optional<User> findById(UserId id) {
        return jpaRepository.findById(id.getValue())
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<User> findByEmail(Email email) {
        return jpaRepository.findByEmail(email.getValue())
                .map(UserPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<User> findAll() {
        return jpaRepository.findAll().stream()
                .map(UserPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByEmail(Email email) {
        return jpaRepository.countByEmail(email.getValue()) > 0;
    }

    @Override
    public boolean existsById(UserId id) {
        return jpaRepository.existsById(id.getValue());
    }
}