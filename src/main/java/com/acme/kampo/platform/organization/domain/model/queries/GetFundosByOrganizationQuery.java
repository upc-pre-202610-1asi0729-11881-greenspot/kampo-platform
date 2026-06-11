package com.acme.kampo.platform.organization.domain.model.queries;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;
public record GetFundosByOrganizationQuery(OrganizationId organizationId) {
    public GetFundosByOrganizationQuery { if (organizationId == null) throw new IllegalArgumentException("organizationId must not be null"); }
}
