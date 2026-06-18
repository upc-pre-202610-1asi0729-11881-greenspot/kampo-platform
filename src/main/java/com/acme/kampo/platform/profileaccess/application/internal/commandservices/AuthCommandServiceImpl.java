package com.acme.kampo.platform.profileaccess.application.internal.commandservices;

import com.acme.kampo.platform.profileaccess.application.commandservices.AuthCommandService;
import com.acme.kampo.platform.profileaccess.application.security.JwtTokenProvider;
import com.acme.kampo.platform.profileaccess.domain.model.commands.LoginCommand;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.Email;
import com.acme.kampo.platform.profileaccess.domain.repositories.UserRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link AuthCommandService}.
 *
 * <p>Authentication flow:
 * <ol>
 *   <li>Find user by email — 401 if not found.</li>
 *   <li>Verify raw password against stored hash — 401 if mismatch.</li>
 *   <li>Generate and return a JWT token.</li>
 * </ol>
 *
 * <p>Both "user not found" and "wrong password" return the same
 * {@code INVALID_CREDENTIALS} error to prevent email enumeration attacks.</p>
 */
@Service
@Transactional(readOnly = true)
public class AuthCommandServiceImpl implements AuthCommandService {

    private static final ApplicationError INVALID_CREDENTIALS =
            ApplicationError.unauthorized("INVALID_CREDENTIALS",
                    "Email or password is incorrect");

    private final UserRepository   userRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder  passwordEncoder;

    public AuthCommandServiceImpl(UserRepository userRepository,
                                  JwtTokenProvider jwtTokenProvider,
                                  PasswordEncoder passwordEncoder) {
        this.userRepository   = userRepository;
        this.jwtTokenProvider = jwtTokenProvider;
        this.passwordEncoder  = passwordEncoder;
    }

    @Override
    public Result<String, ApplicationError> handle(LoginCommand command) {
        var userOpt = userRepository.findByEmail(Email.of(command.email()));
        if (userOpt.isEmpty())
            return Result.failure(INVALID_CREDENTIALS);

        var user = userOpt.get();
        if (!user.verifyPassword(command.rawPassword(), passwordEncoder))
            return Result.failure(INVALID_CREDENTIALS);

        try {
            var token = jwtTokenProvider.generateToken(user);
            return Result.success(token);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AuthCommandService.handle(LoginCommand)", e.getMessage()));
        }
    }
}