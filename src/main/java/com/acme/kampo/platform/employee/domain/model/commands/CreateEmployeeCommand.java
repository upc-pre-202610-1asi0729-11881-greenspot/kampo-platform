package com.acme.kampo.platform.employee.domain.model.commands;

public record CreateEmployeeCommand(
        String name,
        String email,
        String role,
        Long fieldId
) {
}
