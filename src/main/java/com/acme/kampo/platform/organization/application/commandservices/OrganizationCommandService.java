package com.acme.kampo.platform.organization.application.commandservices;

import com.acme.kampo.platform.organization.domain.model.aggregates.*;
import com.acme.kampo.platform.organization.domain.model.commands.*;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for Organization, Fundo, Field and Crop write operations.
 */
public interface OrganizationCommandService {

    Result<Organization, ApplicationError> handle(CreateOrganizationCommand command);

    Result<Organization, ApplicationError> handle(UpdateOrganizationCommand command);

    Result<Void, ApplicationError> handle(DeleteOrganizationCommand command);

    Result<Fundo, ApplicationError> handle(RegisterFundoCommand command);

    Result<Field, ApplicationError> handle(RegisterFieldCommand command);

    Result<Crop, ApplicationError> handle(RegisterCropCommand command);
}
