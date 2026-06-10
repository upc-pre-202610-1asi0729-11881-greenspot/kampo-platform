package com.acme.kampo.platform.alert.application.commandservices;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.domain.model.commands.EvaluateConditionCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for AlertRule write operations.
 */
public interface AlertRuleCommandService {
    Result<AlertRule, ApplicationError> handle(ConfigureAlertRuleCommand command);
    Result<Boolean, ApplicationError> handle(EvaluateConditionCommand command);
}