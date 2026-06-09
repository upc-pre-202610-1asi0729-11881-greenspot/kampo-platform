package com.acme.kampo.platform.alert.application.queryservices;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.queries.GetAlertRuleByIdQuery;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertRulesQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for AlertRule read operations.
 */
public interface AlertRuleQueryService {
    Optional<AlertRule> handle(GetAlertRuleByIdQuery query);
    List<AlertRule> handle(GetAllAlertRulesQuery query);
}