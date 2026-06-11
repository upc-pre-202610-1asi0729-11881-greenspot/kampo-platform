package com.acme.kampo.platform.organization.application.internal.eventhandlers;

import com.acme.kampo.platform.organization.domain.model.events.OrganizationCreatedEvent;
import com.acme.kampo.platform.organization.interfaces.events.OrganizationCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for the {@link OrganizationCreatedEvent} domain event.
 * Translates it into an {@link OrganizationCreatedIntegrationEvent} for cross-context consumers.
 */
@Service("organizationOrganizationCreatedEventHandler")
public class OrganizationsOrganizationCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public OrganizationsOrganizationCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(OrganizationCreatedEvent event) {
        eventPublisher.publishEvent(
                OrganizationCreatedIntegrationEvent.from(event.organization()));
    }
}