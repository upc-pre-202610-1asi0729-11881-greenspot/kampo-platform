package com.acme.kampo.platform.field.application.commandservices;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.commands.CompleteFieldVisitCommand;
import com.acme.kampo.platform.field.domain.model.commands.ScheduleFieldVisitCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link FieldVisit} write operations.
 */
public interface FieldVisitCommandService {

    Result<FieldVisit, ApplicationError> handle(ScheduleFieldVisitCommand command);

    Result<FieldVisit, ApplicationError> handle(CompleteFieldVisitCommand command);
}