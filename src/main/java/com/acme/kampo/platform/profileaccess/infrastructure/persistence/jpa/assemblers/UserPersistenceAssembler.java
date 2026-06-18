package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.UserPersistenceEntity;

/**
 * Static assembler between the {@link User} aggregate and its JPA persistence representation.
 */
public final class UserPersistenceAssembler {

    private UserPersistenceAssembler() {}

    public static User toDomainFromPersistence(UserPersistenceEntity entity) {
        return new User(
                entity.getId(),
                entity.getFirstName(),
                entity.getLastName(),
                entity.getEmail(),
                entity.getPhone(),
                entity.getPasswordHash());
    }

    public static UserPersistenceEntity toPersistenceFromDomain(User user) {
        var entity = new UserPersistenceEntity();
        entity.setId(user.getId() != null ? user.getId().getValue() : null);
        entity.setFirstName(user.getFirstName());
        entity.setLastName(user.getLastName());
        entity.setEmail(user.getEmail().getValue());
        entity.setPhone(user.getPhone());
        entity.setPasswordHash(user.getPasswordHash().getHash());
        return entity;
    }
}