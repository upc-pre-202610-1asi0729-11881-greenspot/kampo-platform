package com.acme.kampo.platform.alert.application.internal.commandservices;

import com.acme.kampo.platform.alert.application.commandservices.AlertCommandService;
import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.commands.MarkAlertReadCommand;
import com.acme.kampo.platform.alert.domain.model.commands.SendAlertCommand;
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

    private final AlertRepository alertRepository;
    private final AlertRuleRepository alertRuleRepository;

    public AlertCommandServiceImpl(AlertRepository alertRepository,
                                   AlertRuleRepository alertRuleRepository) {
        this.alertRepository = alertRepository;
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public Result<Alert, ApplicationError> handle(SendAlertCommand command) {
        var alertRuleOpt = alertRuleRepository.findById(command.alertRuleId());
        if (alertRuleOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "ALERT_RULE", String.valueOf(command.alertRuleId())));
        }
        try {
            var alert = alertRepository.save(new Alert(command));
            return Result.success(alert);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AlertCommandService.handle(SendAlertCommand)",
                    e.getMessage()));
        }
    }

    @Override
    public Result<Alert, ApplicationError> handle(MarkAlertReadCommand command) {
        var alertOpt = alertRepository.findById(command.alertId());
        if (alertOpt.isEmpty()) {
            return Result.failure(ApplicationError.notFound(
                    "ALERT", String.valueOf(command.alertId())));
        }
        var alert = alertOpt.get();
        try {
            alert.markAsRead(command);
            var saved = alertRepository.save(alert);
            return Result.success(saved);
        } catch (IllegalStateException e) {
            return Result.failure(ApplicationError.businessRuleViolation(
                    "ALERT_ALREADY_READ", e.getMessage()));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "AlertCommandService.handle(MarkAlertReadCommand)",
                    e.getMessage()));
        }
    }
}