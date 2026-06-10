package com.acme.kampo.platform.alert.application.queryservices;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.queries.*;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Alert} and {@link AlertRule} read operations.
 *
 * <p>Also handles the evaluation query — {@link EvaluateAlertRuleQuery} — which
 * was moved here from the command service because evaluation is a pure read
 * operation that does not mutate any aggregate state.</p>
 */
public interface AlertQueryService {

    Optional<Alert>     handle(GetAlertByIdQuery query);

    List<Alert>         handle(GetAllAlertsQuery query);

    Optional<AlertRule> handle(GetAlertRuleByIdQuery query);

    List<AlertRule>     handle(GetAllAlertRulesQuery query);

    /**
     * Evaluates whether a sensor reading violates an alert rule's condition.
     *
     * @param query carries the rule ID and the current sensor value
     * @return {@link Result} with {@code true} if the condition is met,
     *         or an {@link ApplicationError} if the rule does not exist
     */
    Result<Boolean, ApplicationError> handle(EvaluateAlertRuleQuery query);
}