package com.acme.kampo.platform.alert.interfaces.rest.transform;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.commands.SendAlertCommand;
import com.acme.kampo.platform.alert.interfaces.rest.resources.AlertResource;
import com.acme.kampo.platform.alert.interfaces.rest.resources.CreateAlertResource;

/**
 * Assembler that converts between {@link Alert} and its REST resources.
 * Static utility class — no state, no Spring bean needed.
 */
public class AlertResourceAssembler {

    private AlertResourceAssembler() {}

    public static SendAlertCommand toCommand(CreateAlertResource resource) {
        return new SendAlertCommand(
                resource.message(),
                resource.priority(),
                resource.fieldId(),
                resource.alertRuleId()
        );
    }

    public static AlertResource toResource(Alert alert) {
        return new AlertResource(
                alert.getId().getValue(),
                alert.getMessage(),
                alert.getPriority(),
                alert.isRead(),
                alert.getFieldId().getValue(),
                alert.getAlertRuleId().getValue()
        );
    }
}