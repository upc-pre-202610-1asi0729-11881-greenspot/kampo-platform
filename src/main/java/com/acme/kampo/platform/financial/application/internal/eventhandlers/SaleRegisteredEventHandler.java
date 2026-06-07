package com.acme.kampo.platform.financial.application.internal.eventhandlers;


import com.acme.kampo.platform.financial.domain.model.events.SaleRegisteredEvent;
import com.acme.kampo.platform.financial.interfaces.events.SaleRegisteredIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for {@link SaleRegisteredEvent}.
 */
@Service("financialSaleRegisteredEventHandler")
public class SaleRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public SaleRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(SaleRegisteredEvent event) {
        eventPublisher.publishEvent(SaleRegisteredIntegrationEvent.from(event.sale()));
    }
}