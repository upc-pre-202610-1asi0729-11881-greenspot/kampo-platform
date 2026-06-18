package com.acme.kampo.platform.employee.domain.model.commands;

public record ModifyEmployeeCommand(
        Long employeeId,
        String name,
        String email,
        String role
) {
}
