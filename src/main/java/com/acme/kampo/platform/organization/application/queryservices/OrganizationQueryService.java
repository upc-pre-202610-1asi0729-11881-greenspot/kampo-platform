package com.acme.kampo.platform.organization.application.queryservices;

import com.acme.kampo.platform.organization.domain.model.aggregates.*;
import com.acme.kampo.platform.organization.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Organization, Fundo, Field and Crop read operations.
 */
public interface OrganizationQueryService {

    Optional<Organization> handle(GetOrganizationByIdQuery query);
    List<Organization>     handle(GetAllOrganizationsQuery query);
    Optional<Fundo>        handle(GetFundoByIdQuery query);
    List<Fundo>            handle(GetFundosByOrganizationQuery query);
    Optional<Field>        handle(GetFieldByIdQuery query);
    List<Field>            handle(GetFieldsByFundoQuery query);
    Optional<Crop>         handle(GetCropByIdQuery query);
    List<Crop>             handle(GetCropsByFieldQuery query);
}
