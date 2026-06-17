package com.acme.kampo.platform.organization.application.acl;

import com.acme.kampo.platform.organization.application.commandservices.OrganizationCommandService;
import com.acme.kampo.platform.organization.application.queryservices.OrganizationQueryService;
import com.acme.kampo.platform.organization.domain.model.commands.CreateOrganizationCommand;
import com.acme.kampo.platform.organization.domain.model.queries.GetAllOrganizationsQuery;
import com.acme.kampo.platform.organization.interfaces.acl.OrganizationsContextFacade;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link OrganizationsContextFacade}.
 *
 * <p>Returns only primitive types to consumers — never internal aggregates
 * or value objects from this bounded context.</p>
 */
@Service
public class OrganizationsContextFacadeImpl implements OrganizationsContextFacade {

    private final OrganizationCommandService commandService;
    private final OrganizationQueryService   queryService;

    public OrganizationsContextFacadeImpl(OrganizationCommandService commandService,
                                          OrganizationQueryService queryService) {
        this.commandService = commandService;
        this.queryService   = queryService;
    }

    @Override
    public Long createOrganization(String name, String address) {
        var result = commandService.handle(new CreateOrganizationCommand(name, address));
        return switch (result) {
            case Result.Success<?, ?> s ->
                    ((com.acme.kampo.platform.organization.domain.model.aggregates.Organization) s.value())
                            .getId().getValue();
            case Result.Failure<?, ?> f ->
                    throw new IllegalStateException("Could not create organization: " + f.error());
        };
    }

    @Override
    public Optional<Long> fetchOrganizationIdByName(String name) {
        return queryService.handle(new GetAllOrganizationsQuery())
                .stream()
                .filter(o -> o.getName().equals(name))
                .findFirst()
                .map(o -> o.getId().getValue());
    }
}
