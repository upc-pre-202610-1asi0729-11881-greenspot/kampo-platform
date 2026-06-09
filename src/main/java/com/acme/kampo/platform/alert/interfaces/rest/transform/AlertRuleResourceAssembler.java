package com.acme.kampo.platform.alert.interfaces.rest.transform;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.interfaces.rest.resources.AlertRuleResource;
import com.acme.kampo.platform.alert.interfaces.rest.resources.CreateAlertRuleResource;

/**
 * Assembler that converts between {@link AlertRule} and its REST resources.
 * Static utility class — no state, no Spring bean needed.
 */
public class AlertRuleResourceAssembler {

    private AlertRuleResourceAssembler() {}

    public static ConfigureAlertRuleCommand toCommand(CreateAlertRuleResource resource) {
        return new ConfigureAlertRuleCommand(
                resource.readingType(),
                resource.conditionOperator(),
                resource.severity(),
                resource.fieldId()
        );
    }

    public static AlertRuleResource toResource(AlertRule alertRule) {
        return new AlertRuleResource(
                alertRule.getId().getValue(),
                alertRule.getReadingType(),
                alertRule.getConditionOperator(),
                alertRule.getSeverity(),
                alertRule.getFieldId().getValue()
        );
    }
}