package com.acme.kampo.platform.alert.interfaces.rest.transform;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.domain.model.commands.SendAlertCommand;
import com.acme.kampo.platform.alert.domain.model.queries.EvaluateAlertRuleQuery;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.interfaces.rest.resources.*;

/**
 * Assembler for Alert and AlertRule resource ↔ command/query/domain translations.
 */
public final class AlertResourceAssembler {

    private AlertResourceAssembler() {}

    public static ConfigureAlertRuleCommand toCommand(CreateAlertRuleResource resource) {
        return new ConfigureAlertRuleCommand(
                resource.readingType(), resource.conditionOperator(),
                resource.threshold(), resource.severity(), resource.fieldId());
    }

    public static SendAlertCommand toCommand(CreateAlertResource resource) {
        return new SendAlertCommand(
                resource.message(), resource.priority(),
                resource.fieldId(), resource.alertRuleId());
    }

    public static EvaluateAlertRuleQuery toQuery(Long alertRuleId,
                                                 EvaluateAlertRuleResource resource) {
        return new EvaluateAlertRuleQuery(AlertRuleId.of(alertRuleId), resource.currentValue());
    }

    public static AlertRuleResource toResource(AlertRule rule) {
        return new AlertRuleResource(
                rule.getId().getValue(),
                rule.getReadingType(),
                rule.getConditionOperator(),
                rule.getThreshold(),
                rule.getSeverity(),
                rule.getFieldId().getValue());
    }

    public static AlertResource toResource(Alert alert) {
        return new AlertResource(
                alert.getId().getValue(),
                alert.getMessage(),
                alert.getPriority(),
                alert.getIsRead(),
                alert.getFieldId().getValue(),
                alert.getAlertRuleId().getValue());
    }
}
