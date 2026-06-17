package com.acme.kampo.platform.field.interfaces.rest.transform;

import com.acme.kampo.platform.field.domain.model.aggregates.FieldVisit;
import com.acme.kampo.platform.field.domain.model.aggregates.Observation;
import com.acme.kampo.platform.field.domain.model.commands.RegisterObservationCommand;
import com.acme.kampo.platform.field.domain.model.commands.ScheduleFieldVisitCommand;
import com.acme.kampo.platform.field.interfaces.rest.resources.*;

/**
 * Static assembler for all resource ↔ command/domain translations in the fieldoperation context.
 */
public final class FieldOperationResourceAssembler {

    private FieldOperationResourceAssembler() {}

    // ── FieldVisit ────────────────────────────────────────────────────────────

    public static ScheduleFieldVisitCommand toCommand(CreateFieldVisitResource resource) {
        return new ScheduleFieldVisitCommand(
                resource.fieldId(), resource.agentId(), resource.scheduledAt());
    }

    public static FieldVisitResource toResource(FieldVisit fieldVisit) {
        return new FieldVisitResource(
                fieldVisit.getId().getValue(),
                fieldVisit.getFieldId().getValue(),
                fieldVisit.getAgentId(),
                fieldVisit.getScheduledAt(),
                fieldVisit.getDoneAt(),
                fieldVisit.getStatus());
    }

    // ── Observation ───────────────────────────────────────────────────────────

    public static RegisterObservationCommand toCommand(CreateObservationResource resource) {
        return new RegisterObservationCommand(
                resource.fieldVisitId(),
                resource.notes(),
                resource.pestName(),
                resource.pestSeverity(),
                resource.diseaseName(),
                resource.diseaseSeverity(),
                resource.recommendation(),
                resource.evidenceUrl());
    }

    public static ObservationResource toResource(Observation observation) {
        return new ObservationResource(
                observation.getId().getValue(),
                observation.getFieldVisitId().getValue(),
                observation.getNotes(),
                observation.getPestName(),
                observation.getPestSeverity(),
                observation.getDiseaseName(),
                observation.getDiseaseSeverity(),
                observation.getRecommendation(),
                observation.getEvidenceUrl());
    }
}