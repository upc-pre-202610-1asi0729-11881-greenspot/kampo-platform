package com.acme.kampo.platform.profileaccess.application.queryservices;

import com.acme.kampo.platform.profileaccess.domain.model.aggregates.User;
import com.acme.kampo.platform.profileaccess.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link User} read operations.
 */
public interface UserQueryService {

    Optional<User> handle(GetUserByIdQuery query);

    List<User>     handle(GetAllUsersQuery query);

    Optional<User> handle(GetUserByEmailQuery query);
}