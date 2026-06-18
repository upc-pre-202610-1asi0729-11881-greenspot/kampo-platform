package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.UserRole;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.profileaccess.domain.repositories.UserRoleRepository;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers.UserRolePersistenceAssembler;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories.UserRoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRoleRepositoryImpl implements UserRoleRepository {

    private final UserRoleJpaRepository jpaRepository;

    public UserRoleRepositoryImpl(UserRoleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public UserRole save(UserRole userRole) {
        var entity = UserRolePersistenceAssembler.toPersistenceFromDomain(userRole);
        return UserRolePersistenceAssembler.toDomainFromPersistence(jpaRepository.save(entity));
    }

    @Override
    public List<UserRole> findByUserId(UserId userId) {
        return jpaRepository.findByUserId(userId.getValue()).stream()
                .map(UserRolePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<UserRole> findByRoleId(RoleId roleId) {
        return jpaRepository.findByRoleId(roleId.getValue()).stream()
                .map(UserRolePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByUserIdAndRoleId(UserId userId, RoleId roleId) {
        return jpaRepository.countByUserIdAndRoleId(userId.getValue(), roleId.getValue()) > 0;
    }
}