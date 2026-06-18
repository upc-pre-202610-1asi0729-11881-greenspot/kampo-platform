package com.acme.kampo.platform.employee.interfaces.rest.resources;

public record EmployeeResource(
        Long id,
        String name,
        String email,
        String role,
        String status,
        Long fieldId
) {}