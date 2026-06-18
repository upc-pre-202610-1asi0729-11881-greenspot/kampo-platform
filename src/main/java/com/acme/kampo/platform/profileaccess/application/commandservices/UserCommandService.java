package com.acme.kampo.platform.profileaccess.application.commandservices;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import com.acme.kampo.platform.profileaccess.domain.model.commands.ModifyProfileCommand;
import com.acme.kampo.platform.profileaccess.domain.model.commands.RegisterUserCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link User} write operations.
 */
public interface UserCommandService {

    Result<User, ApplicationError> handle(RegisterUserCommand command);

    Result<User, ApplicationError> handle(ModifyProfileCommand command);
}