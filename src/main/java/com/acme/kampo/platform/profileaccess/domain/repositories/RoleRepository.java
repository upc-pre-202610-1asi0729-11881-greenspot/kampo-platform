package com.acme.kampo.platform.profileaccess.domain.repositories;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role;
import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Role} aggregate.
 */
public interface RoleRepository {

    Role save(Role role);

    Optional<Role> findById(RoleId id);

    Optional<Role> findByPosition(RolePosition position);

    List<Role> findAll();

    boolean existsByPosition(RolePosition position);

    boolean existsById(RoleId id);
}