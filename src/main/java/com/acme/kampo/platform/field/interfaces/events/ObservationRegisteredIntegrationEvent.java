package com.acme.kampo.platform.field.interfaces.events;

import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.enums.Severity;

/**
 * Integration event published by the {@code fieldoperation} bounded context
 * when a new {@link Observation} is registered.
 *
 * <p>Consumers (e.g. the {@code alert} bounded context) can react to this event
 * when {@code pestSeverity} or {@code diseaseSeverity} is HIGH or CRITICAL.</p>
 *
 * @param observationId   the ID of the registered observation
 * @param fieldVisitId    the ID of the associated field visit
 * @param pestName        name of the detected pest (nullable)
 * @param pestSeverity    severity of the pest (nullable)
 * @param diseaseName     name of the detected disease (nullable)
 * @param diseaseSeverity severity of the disease (nullable)
 */
public record ObservationRegisteredIntegrationEvent(
        Long observationId,
        Long fieldVisitId,
        String pestName,
        Severity pestSeverity,
        String diseaseName,
        Severity diseaseSeverity) {

    public static ObservationRegisteredIntegrationEvent from(Observation observation) {
        return new ObservationRegisteredIntegrationEvent(
                observation.getId().getValue(),
                observation.getFieldVisitId().getValue(),
                observation.getPestName(),
                observation.getPestSeverity(),
                observation.getDiseaseName(),
                observation.getDiseaseSeverity());
    }
}