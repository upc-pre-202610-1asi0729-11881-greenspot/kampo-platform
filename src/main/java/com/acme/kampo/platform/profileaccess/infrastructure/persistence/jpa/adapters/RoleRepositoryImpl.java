package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role;
import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.repositories.RoleRepository;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers.RolePersistenceAssembler;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories.RoleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class RoleRepositoryImpl implements RoleRepository {

    private final RoleJpaRepository jpaRepository;

    public RoleRepositoryImpl(RoleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Role save(Role role) {
        var entity = RolePersistenceAssembler.toPersistenceFromDomain(role);
        return RolePersistenceAssembler.toDomainFromPersistence(jpaRepository.save(entity));
    }

    @Override
    public Optional<Role> findById(RoleId id) {
        return jpaRepository.findById(id.getValue())
                .map(RolePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Role> findByPosition(RolePosition position) {
        return jpaRepository.findByPosition(position)
                .map(RolePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Role> findAll() {
        return jpaRepository.findAll().stream()
                .map(RolePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByPosition(RolePosition position) {
        return jpaRepository.countByPosition(position) > 0;
    }

    @Override
    public boolean existsById(RoleId id) {
        return jpaRepository.existsById(id.getValue());
    }
}