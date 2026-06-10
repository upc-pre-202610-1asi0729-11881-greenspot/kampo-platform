package com.acme.kampo.platform.alert.application.commandservices;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.commands.MarkAlertReadCommand;
import com.acme.kampo.platform.alert.domain.model.commands.SendAlertCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for Alert write operations.
 */
public interface AlertCommandService {
    Result<Alert, ApplicationError> handle(SendAlertCommand command);
    Result<Alert, ApplicationError> handle(MarkAlertReadCommand command);
}