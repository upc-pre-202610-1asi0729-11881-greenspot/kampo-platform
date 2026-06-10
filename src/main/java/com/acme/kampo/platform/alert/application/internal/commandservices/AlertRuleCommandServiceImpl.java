package com.acme.kampo.platform.alert.application.internal.commandservices;

import com.acme.kampo.platform.alert.application.commandservices.AlertRuleCommandService;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.domain.model.commands.EvaluateConditionCommand;
import com.acme.kampo.platform.alert.domain.repositories.AlertRuleRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link AlertRuleCommandService}.
 */
@Service
@Transactional
public class AlertRuleCommandServiceImpl implements AlertRuleCommandService {

    private final AlertRuleRepository alertRuleRepository;

    public AlertRuleCommandServiceImpl(AlertRuleRepository alertRuleRepository) {
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public Result<AlertRule, ApplicationError> handle(ConfigureAlertRuleCommand command) {
        if (alertRuleRepository.existsByFieldIdAndReadingType(
                command.fieldId(), command.readingType().name())) {
            return Result.failure(ApplicationError.conflict(
                    "ALERT_RULE",
                    "An alert rule for field '%d' and reading type '%s' already exists"
                            .formatted(command.fieldId(), command.readingType())));
        }
        try {
            var alertRule = alertRuleRepository.save(new AlertRule(command));
            return Result.success(alertRule);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AlertRuleCommandService.handle(ConfigureAlertRuleCommand)",
                    e.getMessage()));
        }
    }

    @Override
    public Result<Boolean, ApplicationError> handle(EvaluateConditionCommand command) {
        var alertRuleOpt = alertRuleRepository.findById(command.alertRuleId());
        if (alertRuleOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "ALERT_RULE", String.valueOf(command.alertRuleId())));
        }
        try {
            var result = alertRuleOpt.get().evaluate(command);
            return Result.success(result);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AlertRuleCommandService.handle(EvaluateConditionCommand)",
                    e.getMessage()));
        }
    }
}