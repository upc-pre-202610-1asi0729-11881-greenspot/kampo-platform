package com.acme.kampo.platform.profileaccess.application.internal.eventhandlers;

import com.acme.kampo.platform.profileaccess.domain.model.events.UserRegisteredEvent;
import com.acme.kampo.platform.profileaccess.interfaces.events.UserRegisteredIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for {@link UserRegisteredEvent}.
 * Republishes as {@link UserRegisteredIntegrationEvent} for cross-context consumers.
 */
@Service("profileaccessUserRegisteredEventHandler")
public class UserRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public UserRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(UserRegisteredEvent event) {
        eventPublisher.publishEvent(
                UserRegisteredIntegrationEvent.from(event.user()));
    }
}