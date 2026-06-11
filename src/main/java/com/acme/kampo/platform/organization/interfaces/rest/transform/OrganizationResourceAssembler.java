package com.acme.kampo.platform.organization.interfaces.rest.transform;

import com.acme.kampo.platform.organization.domain.model.aggregates.*;
import com.acme.kampo.platform.organization.domain.model.commands.*;
import com.acme.kampo.platform.organization.interfaces.rest.resources.*;

/**
 * Static assembler for all resource ↔ command/domain translations in the organization context.
 */
public final class OrganizationResourceAssembler {

    private OrganizationResourceAssembler() {}

    // ── Organization ──────────────────────────────────────────────────────────

    public static CreateOrganizationCommand toCommand(CreateOrganizationResource r) {
        return new CreateOrganizationCommand(r.name(), r.address());
    }

    public static UpdateOrganizationCommand toCommand(Long id, UpdateOrganizationResource r) {
        return new UpdateOrganizationCommand(id, r.name(), r.address());
    }

    public static OrganizationResource toResource(Organization org) {
        return new OrganizationResource(
                org.getId().getValue(), org.getName(), org.getAddress());
    }

    // ── Fundo ─────────────────────────────────────────────────────────────────

    public static RegisterFundoCommand toCommand(RegisterFundoResource r) {
        return new RegisterFundoCommand(r.organizationId(), r.name(), r.latitude(), r.longitude());
    }

    public static FundoResource toResource(Fundo fundo) {
        return new FundoResource(
                fundo.getId().getValue(),
                fundo.getOrganizationId().getValue(),
                fundo.getName(),
                fundo.getLocation().latitude(),
                fundo.getLocation().longitude());
    }

    // ── Field ─────────────────────────────────────────────────────────────────

    public static RegisterFieldCommand toCommand(RegisterFieldResource r) {
        return new RegisterFieldCommand(r.fundoId(), r.name(), r.areaSqm());
    }

    public static FieldResource toResource(Field field) {
        return new FieldResource(
                field.getId().getValue(),
                field.getFundoId().getValue(),
                field.getName(),
                field.getAreaSqm());
    }

    // ── Crop ──────────────────────────────────────────────────────────────────

    public static RegisterCropCommand toCommand(RegisterCropResource r) {
        return new RegisterCropCommand(r.fieldId(), r.name(), r.plantedAt());
    }

    public static CropResource toResource(Crop crop) {
        return new CropResource(
                crop.getId().getValue(),
                crop.getFieldId().getValue(),
                crop.getName(),
                crop.getPlantedAt());
    }
}