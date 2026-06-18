package com.acme.kampo.platform.profileaccess.application.acl;

import com.acme.kampo.platform.profileaccess.application.queryservices.RoleQueryService;
import com.acme.kampo.platform.profileaccess.application.queryservices.UserQueryService;
import com.acme.kampo.platform.profileaccess.domain.model.queries.GetRolesByUserQuery;
import com.acme.kampo.platform.profileaccess.domain.model.queries.GetUserByEmailQuery;
import com.acme.kampo.platform.profileaccess.domain.model.valueobjects.UserId;
import com.acme.kampo.platform.profileaccess.interfaces.acl.ProfileAccessContextFacade;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of {@link ProfileAccessContextFacade}.
 * Returns only primitive types — never internal aggregates or value objects.
 */
@Service
public class ProfileAccessContextFacadeImpl implements ProfileAccessContextFacade {

    private final UserQueryService userQueryService;
    private final RoleQueryService roleQueryService;

    public ProfileAccessContextFacadeImpl(UserQueryService userQueryService,
                                          RoleQueryService roleQueryService) {
        this.userQueryService = userQueryService;
        this.roleQueryService = roleQueryService;
    }

    @Override
    public Optional<Long> fetchUserIdByEmail(String email) {
        return userQueryService.handle(new GetUserByEmailQuery(email))
                .map(u -> u.getId().getValue());
    }

    @Override
    public List<String> fetchRolesByUserId(Long userId) {
        return roleQueryService.handle(new GetRolesByUserQuery(UserId.of(userId)))
                .stream()
                .map(r -> r.getPosition().name())
                .toList();
    }
}