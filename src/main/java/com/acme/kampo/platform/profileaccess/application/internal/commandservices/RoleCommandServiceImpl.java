package com.acme.kampo.platform.profileaccess.application.internal.commandservices;

import com.acme.kampo.platform.profileaccess.application.commandservices.RoleCommandService;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.*;
import com.acme.kampo.platform.profileaccess.domain.model.commands.*;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.PermissionId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.RoleId;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.profileaccess.domain.repositories.*;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link RoleCommandService}.
 */
@Service
@Transactional
public class RoleCommandServiceImpl implements RoleCommandService {

    private final RoleRepository           roleRepository;
    private final PermissionRepository     permissionRepository;
    private final UserRoleRepository       userRoleRepository;
    private final RolePermissionRepository rolePermissionRepository;
    private final UserRepository           userRepository;

    public RoleCommandServiceImpl(RoleRepository roleRepository,
                                  PermissionRepository permissionRepository,
                                  UserRoleRepository userRoleRepository,
                                  RolePermissionRepository rolePermissionRepository,
                                  UserRepository userRepository) {
        this.roleRepository           = roleRepository;
        this.permissionRepository     = permissionRepository;
        this.userRoleRepository       = userRoleRepository;
        this.rolePermissionRepository = rolePermissionRepository;
        this.userRepository           = userRepository;
    }

    @Override
    public Result<Role, ApplicationError> handle(CreateRoleCommand command) {
        if (roleRepository.existsByPosition(command.position()))
            return Result.failure(ApplicationError.conflict(
                    "ROLE", "A role with position '%s' already exists".formatted(command.position())));
        try {
            return Result.success(roleRepository.save(new Role(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "RoleCommandService.handle(CreateRoleCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Permission, ApplicationError> handle(CreatePermissionCommand command) {
        if (permissionRepository.existsByCategory(command.category()))
            return Result.failure(ApplicationError.conflict(
                    "PERMISSION", "A permission for category '%s' already exists".formatted(command.category())));
        try {
            return Result.success(permissionRepository.save(new Permission(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "RoleCommandService.handle(CreatePermissionCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<UserRole, ApplicationError> handle(AssignRoleCommand command) {
        if (!userRepository.existsById(UserId.of(command.userId())))
            return Result.failure(ApplicationError.notFound("USER", String.valueOf(command.userId())));
        if (!roleRepository.existsById(RoleId.of(command.roleId())))
            return Result.failure(ApplicationError.notFound("ROLE", String.valueOf(command.roleId())));
        if (userRoleRepository.existsByUserIdAndRoleId(UserId.of(command.userId()), RoleId.of(command.roleId())))
            return Result.failure(ApplicationError.conflict(
                    "USER_ROLE", "User %d already has role %d".formatted(command.userId(), command.roleId())));
        try {
            return Result.success(userRoleRepository.save(new UserRole(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "RoleCommandService.handle(AssignRoleCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<RolePermission, ApplicationError> handle(AssignPermissionCommand command) {
        if (!roleRepository.existsById(RoleId.of(command.roleId())))
            return Result.failure(ApplicationError.notFound("ROLE", String.valueOf(command.roleId())));
        if (!permissionRepository.existsById(PermissionId.of(command.permissionId())))
            return Result.failure(ApplicationError.notFound("PERMISSION", String.valueOf(command.permissionId())));
        if (rolePermissionRepository.existsByRoleIdAndPermissionId(
                RoleId.of(command.roleId()), PermissionId.of(command.permissionId())))
            return Result.failure(ApplicationError.conflict(
                    "ROLE_PERMISSION", "Role %d already has permission %d".formatted(command.roleId(), command.permissionId())));
        try {
            return Result.success(rolePermissionRepository.save(new RolePermission(command)));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "RoleCommandService.handle(AssignPermissionCommand)", e.getMessage()));
        }
    }
}