package com.acme.kampo.platform.alert.application.acl;

import com.acme.kampo.platform.alert.application.commandservices.AlertCommandService;
import com.acme.kampo.platform.alert.application.commandservices.AlertRuleCommandService;
import com.acme.kampo.platform.alert.application.queryservices.AlertQueryService;
import com.acme.kampo.platform.alert.application.queryservices.AlertRuleQueryService;
import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertRulesQuery;
import com.acme.kampo.platform.alert.interfaces.acl.AlertContextFacade;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link AlertContextFacade}.
 */
@Service
public class AlertsContextFacadeImpl implements AlertContextFacade {

    private final AlertRuleCommandService alertRuleCommandService;
    private final AlertRuleQueryService alertRuleQueryService;
    private final AlertCommandService alertCommandService;
    private final AlertQueryService alertQueryService;

    public AlertsContextFacadeImpl(AlertRuleCommandService alertRuleCommandService,
                                   AlertRuleQueryService alertRuleQueryService,
                                   AlertCommandService alertCommandService,
                                   AlertQueryService alertQueryService) {
        this.alertRuleCommandService = alertRuleCommandService;
        this.alertRuleQueryService = alertRuleQueryService;
        this.alertCommandService = alertCommandService;
        this.alertQueryService = alertQueryService;
    }

    @Override
    public Long createAlertRule(ReadingType readingType, ConditionOperator conditionOperator,
                                SeverityLevel severity, Long fieldId) {
        var result = alertRuleCommandService.handle(
                new ConfigureAlertRuleCommand(readingType, conditionOperator, severity, fieldId));
        return switch (result) {
            case Result.Success<?, ?> s ->
                    ((com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule) s.value())
                            .getId().getValue();
            case Result.Failure<?, ?> f ->
                    throw new IllegalStateException("Could not create alert rule: " + f.error());
        };
    }

    @Override
    public Optional<Long> fetchAlertRuleIdByFieldId(Long fieldId) {
        return alertRuleQueryService.handle(new GetAllAlertRulesQuery())
                .stream()
                .filter(rule -> rule.getFieldId().getValue().equals(fieldId))
                .findFirst()
                .map(rule -> rule.getId().getValue());
    }
}