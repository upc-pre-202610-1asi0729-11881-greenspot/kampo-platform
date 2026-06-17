package com.acme.kampo.platform.field.application.commandservices;

import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.commands.RegisterObservationCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Observation} write operations.
 */
public interface ObservationCommandService {

    Result<Observation, ApplicationError> handle(RegisterObservationCommand command);
}