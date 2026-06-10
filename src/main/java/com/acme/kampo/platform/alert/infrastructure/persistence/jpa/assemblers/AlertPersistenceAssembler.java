package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities.AlertPersistenceEntity;

/**
 * Static assembler between the {@link Alert} aggregate and its JPA persistence representation.
 */
public final class AlertPersistenceAssembler {

    private AlertPersistenceAssembler() {}

    public static Alert toDomainFromPersistence(AlertPersistenceEntity entity) {
        return new Alert(
                entity.getId(),
                entity.getMessage(),
                entity.getPriority(),
                entity.isRead(),
                entity.getFieldId(),
                entity.getAlertRuleId());
    }

    public static AlertPersistenceEntity toPersistenceFromDomain(Alert alert) {
        var entity = new AlertPersistenceEntity();
        entity.setId(alert.getId() != null ? alert.getId().getValue() : null);
        entity.setMessage(alert.getMessage());
        entity.setPriority(alert.getPriority());
        entity.setRead(alert.getIsRead());
        entity.setFieldId(alert.getFieldId().getValue());
        entity.setAlertRuleId(alert.getAlertRuleId().getValue());
        return entity;
    }
}
