package com.acme.kampo.platform.alert.application.commandservices;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.*;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Alert} and {@link AlertRule} write operations.
 */
public interface AlertCommandService {

    /**
     * Configures a new alert rule for a field.
     *
     * @param command the configuration command
     * @return {@link Result} with the created {@link AlertRule} or an {@link ApplicationError}
     */
    Result<AlertRule, ApplicationError> handle(ConfigureAlertRuleCommand command);

    /**
     * Sends (creates) a new alert triggered by a rule violation.
     *
     * @param command the send command
     * @return {@link Result} with the created {@link Alert} or an {@link ApplicationError}
     */
    Result<Alert, ApplicationError> handle(SendAlertCommand command);

    /**
     * Marks an existing alert as read.
     *
     * @param command the mark-read command
     * @return {@link Result} with the updated {@link Alert} or an {@link ApplicationError}
     */
    Result<Alert, ApplicationError> handle(MarkAlertReadCommand command);
}