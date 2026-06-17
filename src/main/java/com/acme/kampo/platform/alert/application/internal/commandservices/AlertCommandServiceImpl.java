package com.acme.kampo.platform.alert.application.internal.commandservices;

import com.acme.kampo.platform.alert.application.commandservices.AlertCommandService;
import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.*;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.domain.repositories.AlertRepository;
import com.acme.kampo.platform.alert.domain.repositories.AlertRuleRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link AlertCommandService}.
 */
@Service
@Transactional
public class AlertCommandServiceImpl implements AlertCommandService {

    private final AlertRepository     alertRepository;
    private final AlertRuleRepository alertRuleRepository;

    public AlertCommandServiceImpl(AlertRepository alertRepository,
                                   AlertRuleRepository alertRuleRepository) {
        this.alertRepository     = alertRepository;
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public Result<AlertRule, ApplicationError> handle(ConfigureAlertRuleCommand command) {
        try {
            var alertRule = alertRuleRepository.save(new AlertRule(command));
            return Result.success(alertRule);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AlertCommandService.handle(ConfigureAlertRuleCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Alert, ApplicationError> handle(SendAlertCommand command) {
        if (!alertRuleRepository.existsById(AlertRuleId.of(command.alertRuleId())))
            return Result.failure(ApplicationError.notFound(
                    "ALERT_RULE", String.valueOf(command.alertRuleId())));
        try {
            var alert = alertRepository.save(new Alert(command));
            return Result.success(alert);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AlertCommandService.handle(SendAlertCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Alert, ApplicationError> handle(MarkAlertReadCommand command) {
        var alertOpt = alertRepository.findById(AlertId.of(command.alertId()));
        if (alertOpt.isEmpty())
            return Result.failure(ApplicationError.notFound(
                    "ALERT", String.valueOf(command.alertId())));
        var alert = alertOpt.get();
        if (alert.getIsRead())
            return Result.failure(ApplicationError.businessRuleViolation(
                    "ALERT_ALREADY_READ",
                    "Alert %d is already marked as read".formatted(command.alertId())));
        try {
            alert.markAsRead(command);
            return Result.success(alertRepository.save(alert));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AlertCommandService.handle(MarkAlertReadCommand)", e.getMessage()));
        }
    }
}