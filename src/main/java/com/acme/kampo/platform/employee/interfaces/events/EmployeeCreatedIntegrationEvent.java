package com.acme.kampo.platform.employee.interfaces.events;

public record EmployeeCreatedIntegrationEvent(Long employeeId, String name) {
}