package com.acme.kampo.platform.financial.application.internal.eventhandlers;


import com.acme.kampo.platform.financial.domain.model.events.ProfitabilityCalculatedEvent;
import com.acme.kampo.platform.financial.interfaces.events.ProfitabilityCalculatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for {@link ProfitabilityCalculatedEvent}.
 */
@Service("financialProfitabilityCalculatedEventHandler")
public class ProfitabilityCalculatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public ProfitabilityCalculatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(ProfitabilityCalculatedEvent event) {
        eventPublisher.publishEvent(
                ProfitabilityCalculatedIntegrationEvent.from(event.profitability()));
    }
}
