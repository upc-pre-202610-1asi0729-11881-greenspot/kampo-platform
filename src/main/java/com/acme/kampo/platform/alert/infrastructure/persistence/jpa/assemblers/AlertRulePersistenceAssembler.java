package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;

/**
 * Static assembler between the {@link AlertRule} aggregate and its JPA persistence representation.
 */
public final class AlertRulePersistenceAssembler {

    private AlertRulePersistenceAssembler() {}

    public static AlertRule toDomainFromPersistence(AlertRulePersistenceEntity entity) {
        return new AlertRule(
                entity.getId(),
                entity.getReadingType(),
                entity.getConditionOperator(),
                entity.getThreshold(),
                entity.getSeverity(),
                entity.getFieldId());
    }

    public static AlertRulePersistenceEntity toPersistenceFromDomain(AlertRule rule) {
        var entity = new AlertRulePersistenceEntity();
        entity.setId(rule.getId() != null ? rule.getId().getValue() : null);
        entity.setReadingType(rule.getReadingType());
        entity.setConditionOperator(rule.getConditionOperator());
        entity.setThreshold(rule.getThreshold());
        entity.setSeverity(rule.getSeverity());
        entity.setFieldId(rule.getFieldId().getValue());
        return entity;
    }
}