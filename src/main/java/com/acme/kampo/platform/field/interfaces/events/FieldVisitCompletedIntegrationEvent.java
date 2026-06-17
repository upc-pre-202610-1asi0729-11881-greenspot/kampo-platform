package com.acme.kampo.platform.field.interfaces.events;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;

import java.time.LocalDateTime;

/**
 * Integration event published by the {@code fieldoperation} bounded context
 * when a {@link FieldVisit} is completed.
 *
 * @param fieldVisitId the ID of the completed field visit
 * @param fieldId      the ID of the visited field
 * @param agentId      the ID of the agent who carried out the visit
 * @param doneAt       the date and time the visit was completed
 */
public record FieldVisitCompletedIntegrationEvent(
        Long fieldVisitId,
        Long fieldId,
        Long agentId,
        LocalDateTime doneAt) {

    public static FieldVisitCompletedIntegrationEvent from(FieldVisit fieldVisit) {
        return new FieldVisitCompletedIntegrationEvent(
                fieldVisit.getId().getValue(),
                fieldVisit.getFieldId().getValue(),
                fieldVisit.getAgentId(),
                fieldVisit.getDoneAt());
    }
}