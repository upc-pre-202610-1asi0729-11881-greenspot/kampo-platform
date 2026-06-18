package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.RolePermission;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.repositories.RolePermissionRepository;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.assemblers.RolePermissionPersistenceAssembler;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories.RolePermissionJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class RolePermissionRepositoryImpl implements RolePermissionRepository {

    private final RolePermissionJpaRepository jpaRepository;

    public RolePermissionRepositoryImpl(RolePermissionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public RolePermission save(RolePermission rolePermission) {
        var entity = RolePermissionPersistenceAssembler.toPersistenceFromDomain(rolePermission);
        return RolePermissionPersistenceAssembler.toDomainFromPersistence(jpaRepository.save(entity));
    }

    @Override
    public List<RolePermission> findByRoleId(RoleId roleId) {
        return jpaRepository.findByRoleId(roleId.getValue()).stream()
                .map(RolePermissionPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByRoleIdAndPermissionId(RoleId roleId, PermissionId permissionId) {
        return jpaRepository.countByRoleIdAndPermissionId(
                roleId.getValue(), permissionId.getValue()) > 0;
    }
}