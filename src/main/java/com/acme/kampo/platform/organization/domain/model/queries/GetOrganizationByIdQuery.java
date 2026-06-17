package com.acme.kampo.platform.organization.domain.model.queries;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;
public record GetOrganizationByIdQuery(OrganizationId organizationId) {
    public GetOrganizationByIdQuery { if (organizationId == null) throw new IllegalArgumentException("organizationId must not be null"); }
}