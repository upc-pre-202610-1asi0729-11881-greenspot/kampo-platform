package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities.AlertPersistenceEntity;

/**
 * Assembler that converts between {@link Alert} and {@link AlertPersistenceEntity}.
 * Static utility class — no state, no Spring bean needed.
 */
public class AlertPersistenceAssembler {

    private AlertPersistenceAssembler() {}

    public static AlertPersistenceEntity toPersistenceFromDomain(Alert alert) {
        var entity = new AlertPersistenceEntity();
        if (alert.getId() != null)
            entity.setId(alert.getId().getValue());
        entity.setMessage(alert.getMessage());
        entity.setPriority(alert.getPriority());
        entity.setRead(alert.isRead());
        entity.setFieldId(alert.getFieldId().getValue());
        entity.setAlertRuleId(alert.getAlertRuleId().getValue());
        return entity;
    }

    public static Alert toDomainFromPersistence(AlertPersistenceEntity entity) {
        return new Alert(
                entity.getId(),
                entity.getMessage(),
                entity.getPriority(),
                entity.isRead(),
                entity.getFieldId(),
                entity.getAlertRuleId()
        );
    }
}