package com.acme.kampo.platform.employee.interfaces.rest.transform;

import com.acme.kampo.platform.employee.domain.model.commands.CreateEmployeeCommand;
import com.acme.kampo.platform.employee.interfaces.rest.resources.CreateEmployeeResource;

public class CreateEmployeeAssembler {
    public static CreateEmployeeCommand toCommand(CreateEmployeeResource resource) {
        return new CreateEmployeeCommand(
                resource.name(),
                resource.email(),
                resource.role(),
                resource.fieldId()
        );
    }
}