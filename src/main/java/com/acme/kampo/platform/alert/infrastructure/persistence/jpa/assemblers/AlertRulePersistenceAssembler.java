package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;

/**
 * Assembler that converts between {@link AlertRule} and {@link AlertRulePersistenceEntity}.
 * Static utility class — no state, no Spring bean needed.
 */
public class AlertRulePersistenceAssembler {

    private AlertRulePersistenceAssembler() {}

    public static AlertRulePersistenceEntity toPersistenceFromDomain(AlertRule alertRule) {
        var entity = new AlertRulePersistenceEntity();
        if (alertRule.getId() != null)
            entity.setId(alertRule.getId().getValue());
        entity.setReadingType(alertRule.getReadingType());
        entity.setConditionOperator(alertRule.getConditionOperator());
        entity.setSeverity(alertRule.getSeverity());
        entity.setFieldId(alertRule.getFieldId().getValue());
        return entity;
    }

    public static AlertRule toDomainFromPersistence(AlertRulePersistenceEntity entity) {
        return new AlertRule(
                entity.getId(),
                entity.getReadingType(),
                entity.getConditionOperator(),
                entity.getSeverity(),
                entity.getFieldId()
        );
    }
}