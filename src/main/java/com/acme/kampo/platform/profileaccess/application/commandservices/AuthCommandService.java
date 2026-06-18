package com.acme.kampo.platform.profileaccess.application.commandservices;

import com.acme.kampo.platform.profileaccess.domain.model.commands.LoginCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for authentication operations.
 * Returns a JWT token string on successful login.
 */
public interface AuthCommandService {

    /**
     * Authenticates a user and generates a JWT token.
     *
     * @param command carries email and raw password
     * @return {@link Result} with the JWT token string on success,
     *         or {@link ApplicationError} if credentials are invalid
     */
    Result<String, ApplicationError> handle(LoginCommand command);
}