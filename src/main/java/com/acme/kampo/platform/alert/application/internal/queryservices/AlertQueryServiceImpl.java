package com.acme.kampo.platform.alert.application.internal.queryservices;

import com.acme.kampo.platform.alert.application.queryservices.AlertQueryService;
import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.queries.*;
import com.acme.kampo.platform.alert.domain.repositories.AlertRepository;
import com.acme.kampo.platform.alert.domain.repositories.AlertRuleRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link AlertQueryService}.
 *
 * <p>Handles both standard read operations and the evaluation query
 * ({@link EvaluateAlertRuleQuery}) — a pure read that delegates to
 * {@link AlertRule#evaluate(double)} without mutating any state.</p>
 */
@Service
@Transactional(readOnly = true)
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertRepository     alertRepository;
    private final AlertRuleRepository alertRuleRepository;

    public AlertQueryServiceImpl(AlertRepository alertRepository,
                                 AlertRuleRepository alertRuleRepository) {
        this.alertRepository     = alertRepository;
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.alertId());
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }

    @Override
    public Optional<AlertRule> handle(GetAlertRuleByIdQuery query) {
        return alertRuleRepository.findById(query.alertRuleId());
    }

    @Override
    public List<AlertRule> handle(GetAllAlertRulesQuery query) {
        return alertRuleRepository.findAll();
    }

    /**
     * Loads the alert rule and delegates evaluation to {@link AlertRule#evaluate(double)}.
     * Returns {@link Result#failure} if the rule does not exist.
     */
    @Override
    public Result<Boolean, ApplicationError> handle(EvaluateAlertRuleQuery query) {
        var ruleOpt = alertRuleRepository.findById(query.alertRuleId());
        if (ruleOpt.isEmpty())
            return Result.failure(ApplicationError.notFound(
                    "ALERT_RULE", String.valueOf(query.alertRuleId().getValue())));
        var conditionMet = ruleOpt.get().evaluate(query.currentValue());
        return Result.success(conditionMet);
    }
}