package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Permission;
import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;
import com.acme.kampo.platform.profileaccess.domain.repositories.PermissionRepository;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers.PermissionPersistenceAssembler;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories.PermissionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class PermissionRepositoryImpl implements PermissionRepository {

    private final PermissionJpaRepository jpaRepository;

    public PermissionRepositoryImpl(PermissionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Permission save(Permission permission) {
        var entity = PermissionPersistenceAssembler.toPersistenceFromDomain(permission);
        return PermissionPersistenceAssembler.toDomainFromPersistence(jpaRepository.save(entity));
    }

    @Override
    public Optional<Permission> findById(PermissionId id) {
        return jpaRepository.findById(id.getValue())
                .map(PermissionPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Permission> findAll() {
        return jpaRepository.findAll().stream()
                .map(PermissionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByCategory(PermissionCategory category) {
        return jpaRepository.countByCategory(category) > 0;
    }

    @Override
    public boolean existsById(PermissionId id) {
        return jpaRepository.existsById(id.getValue());
    }
}