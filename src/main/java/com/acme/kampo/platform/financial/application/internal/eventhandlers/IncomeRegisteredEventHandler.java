package com.acme.kampo.platform.financial.application.internal.eventhandlers;


import com.acme.kampo.platform.financial.domain.model.events.IncomeRegisteredEvent;
import com.acme.kampo.platform.financial.interfaces.events.IncomeRegisteredIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for {@link IncomeRegisteredEvent}.
 */
@Service("financialIncomeRegisteredEventHandler")
public class IncomeRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public IncomeRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(IncomeRegisteredEvent event) {
        eventPublisher.publishEvent(IncomeRegisteredIntegrationEvent.from(event.income()));
    }
}
