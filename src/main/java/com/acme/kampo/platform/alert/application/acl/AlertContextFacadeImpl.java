package com.acme.kampo.platform.alert.application.acl;

import com.acme.kampo.platform.alert.application.commandservices.AlertCommandService;
import com.acme.kampo.platform.alert.application.queryservices.AlertQueryService;
import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertRulesQuery;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.alert.interfaces.acl.AlertContextFacade;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Implementation of {@link AlertContextFacade}.
 *
 * <p>Converts primitive string inputs to domain enums before delegating
 * to the command/query services. Returns only primitive types to consumers.</p>
 */
@Service
public class AlertContextFacadeImpl implements AlertContextFacade {

    private final AlertCommandService alertCommandService;
    private final AlertQueryService   alertQueryService;

    public AlertContextFacadeImpl(AlertCommandService alertCommandService,
                                  AlertQueryService alertQueryService) {
        this.alertCommandService = alertCommandService;
        this.alertQueryService   = alertQueryService;
    }

    @Override
    public Long createAlertRule(String readingType, String conditionOperator,
                                Double threshold, String severity, Long fieldId) {
        var command = new ConfigureAlertRuleCommand(
                ReadingType.valueOf(readingType),
                ConditionOperator.valueOf(conditionOperator),
                threshold,
                SeverityLevel.valueOf(severity),
                fieldId);
        var result = alertCommandService.handle(command);
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
        return alertQueryService.handle(new GetAllAlertRulesQuery())
                .stream()
                .filter(r -> r.getFieldId().equals(FieldId.of(fieldId)))
                .findFirst()
                .map(r -> r.getId().getValue());
    }
}