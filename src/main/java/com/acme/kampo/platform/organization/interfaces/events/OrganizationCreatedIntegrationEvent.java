package com.acme.kampo.platform.organization.interfaces.events;

import com.acme.kampo.platform.organization.domain.model.aggregates.Organization;

public record OrganizationCreatedIntegrationEvent(
        Long organizationId,
        String name,
        String address) {

    public static OrganizationCreatedIntegrationEvent from(Organization organization) {
        return new OrganizationCreatedIntegrationEvent(
                organization.getId().getValue(),
                organization.getName(),
                organization.getAddress());
    }
}