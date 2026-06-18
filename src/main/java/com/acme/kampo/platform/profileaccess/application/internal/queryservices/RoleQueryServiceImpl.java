package com.acme.kampo.platform.profileaccess.application.internal.queryservices;

import com.acme.kampo.platform.profileaccess.application.queryservices.RoleQueryService;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Permission;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.Role;
import com.acme.kampo.platform.profileaccess.domain.model.queries.*;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;
import com.acme.kampo.platform.profileaccess.domain.repositories.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link RoleQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class RoleQueryServiceImpl implements RoleQueryService {

    private final RoleRepository           roleRepository;
    private final UserRoleRepository       userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final PermissionRepository     permissionRepository;

    public RoleQueryServiceImpl(RoleRepository roleRepository,
                                UserRoleRepository userRoleRepository,
                                RolePermissionRepository rolePermissionRepository,
                                PermissionRepository permissionRepository) {
        this.roleRepository           = roleRepository;
        this.userRoleRepository       = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.permissionRepository     = permissionRepository;
    }

    @Override
    public Optional<Role> handle(GetRoleByIdQuery query) {
        return roleRepository.findById(query.roleId());
    }

    @Override
    public List<Role> handle(GetAllRolesQuery query) {
        return roleRepository.findAll();
    }

    @Override
    public List<Role> handle(GetRolesByUserQuery query) {
        return userRoleRepository.findByUserId(query.userId()).stream()
                .map(ur -> roleRepository.findById(ur.getRoleId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    @Override
    public List<Permission> handle(GetPermissionsByRoleQuery query) {
        return rolePermissionRepository.findByRoleId(query.roleId()).stream()
                .map(rp -> permissionRepository.findById(rp.getPermissionId()))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }
}