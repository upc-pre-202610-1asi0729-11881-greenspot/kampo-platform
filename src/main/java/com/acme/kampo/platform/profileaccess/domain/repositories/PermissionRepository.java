package com.acme.kampo.platform.profileaccess.domain.repositories;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Permission;
import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Permission} aggregate.
 */
public interface PermissionRepository {

    Permission save(Permission permission);

    Optional<Permission> findById(PermissionId id);

    List<Permission> findAll();

    boolean existsByCategory(PermissionCategory category);

    boolean existsById(PermissionId id);
}