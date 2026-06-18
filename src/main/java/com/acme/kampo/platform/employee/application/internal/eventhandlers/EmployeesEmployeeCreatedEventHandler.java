package com.acme.kampo.platform.employee.application.internal.eventhandlers;

import com.acme.kampo.platform.employee.domain.model.events.EmployeeCreatedEvent;
import com.acme.kampo.platform.employee.interfaces.events.EmployeeCreatedIntegrationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EmployeesEmployeeCreatedEventHandler {

    private final ApplicationEventPublisher eventPublisher;

    public EmployeesEmployeeCreatedEventHandler(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }

    @EventListener
    public void on(EmployeeCreatedEvent event) {
        eventPublisher.publishEvent(
                new EmployeeCreatedIntegrationEvent(event.employeeId(), event.name())
        );
    }
}