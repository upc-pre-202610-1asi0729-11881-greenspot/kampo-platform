package com.acme.kampo.platform.employee.interfaces.rest.resources;

public record CreateEmployeeResource(
        String name,
        String email,
        String role,
        Long fieldId
) {}