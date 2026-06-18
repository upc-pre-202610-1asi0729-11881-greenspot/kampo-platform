package com.acme.kampo.platform.employee.interfaces.rest.resources;

public record ModifyEmployeeResource(
        String name,
        String email,
        String role
) {}