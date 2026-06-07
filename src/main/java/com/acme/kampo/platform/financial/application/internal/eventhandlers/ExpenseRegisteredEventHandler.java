package com.acme.kampo.platform.financial.application.internal.eventhandlers;


import com.acme.kampo.platform.financial.domain.model.events.ExpenseRegisteredEvent;
import com.acme.kampo.platform.financial.interfaces.events.ExpenseRegisteredIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

/**
 * Internal application-layer handler for {@link ExpenseRegisteredEvent}.
 * Republishes as {@link ExpenseRegisteredIntegrationEvent} for cross-context consumers.
 */
@Service("financialExpenseRegisteredEventHandler")
public class ExpenseRegisteredEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public ExpenseRegisteredEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(ExpenseRegisteredEvent event) {
        eventPublisher.publishEvent(ExpenseRegisteredIntegrationEvent.from(event.expense()));
    }
}