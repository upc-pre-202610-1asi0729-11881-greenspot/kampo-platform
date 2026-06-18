package com.acme.kampo.platform.profileaccess.application.internal.commandservices;

import com.acme.kampo.platform.profileaccess.application.commandservices.UserCommandService;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import com.acme.kampo.platform.profileaccess.domain.model.aggregates.UserRole;
import com.acme.kampo.platform.profileaccess.domain.model.commands.AssignRoleCommand;
import com.acme.kampo.platform.profileaccess.domain.model.commands.ModifyProfileCommand;
import com.acme.kampo.platform.profileaccess.domain.model.commands.RegisterUserCommand;
import com.acme.kampo.platform.profileaccess.domain.model.enums.RolePosition;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.Email;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.profileaccess.domain.repositories.RoleRepository;
import com.acme.kampo.platform.profileaccess.domain.repositories.UserRepository;
import com.acme.kampo.platform.profileaccess.domain.repositories.UserRoleRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link UserCommandService}.
 *
 * <p>On registration, automatically assigns the default {@link RolePosition#AGRONOMIST}
 * role if it exists in the system. If no default role is found, the user is registered
 * without a role — an admin can assign one manually.</p>
 */
@Service
@Transactional
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository     userRepository;
    private final RoleRepository     roleRepository;
    private final UserRoleRepository userRoleRepository;
    private final PasswordEncoder    passwordEncoder;

    public UserCommandServiceImpl(UserRepository userRepository,
                                  RoleRepository roleRepository,
                                  UserRoleRepository userRoleRepository,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository     = userRepository;
        this.roleRepository     = roleRepository;
        this.userRoleRepository = userRoleRepository;
        this.passwordEncoder    = passwordEncoder;
    }

    @Override
    public Result<User, ApplicationError> handle(RegisterUserCommand command) {
        if (userRepository.existsByEmail(Email.of(command.email())))
            return Result.failure(ApplicationError.conflict(
                    "USER", "A user with email '%s' already exists".formatted(command.email())));
        try {
            var user = userRepository.save(new User(command, passwordEncoder));
            assignDefaultRole(user);
            return Result.success(user);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "UserCommandService.handle(RegisterUserCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<User, ApplicationError> handle(ModifyProfileCommand command) {
        var userOpt = userRepository.findById(UserId.of(command.userId()));
        if (userOpt.isEmpty())
            return Result.failure(ApplicationError.notFound(
                    "USER", String.valueOf(command.userId())));
        try {
            var user = userOpt.get();
            user.modifyProfile(command);
            return Result.success(userRepository.save(user));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "UserCommandService.handle(ModifyProfileCommand)", e.getMessage()));
        }
    }

    /**
     * Assigns the AGRONOMIST role to a newly registered user if the role exists.
     * Silently skips if no default role is configured.
     */
    private void assignDefaultRole(User user) {
        roleRepository.findByPosition(RolePosition.AGRONOMIST).ifPresent(role ->
                userRoleRepository.save(new UserRole(
                        new AssignRoleCommand(
                                user.getId().getValue(),
                                role.getId().getValue()))));
    }
}