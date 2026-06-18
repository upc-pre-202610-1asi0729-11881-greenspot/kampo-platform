package com.acme.kampo.platform.employee.interfaces.rest.transform;

import com.acme.kampo.platform.employee.domain.model.commands.ModifyEmployeeCommand;
import com.acme.kampo.platform.employee.interfaces.rest.resources.ModifyEmployeeResource;

public class ModifyEmployeeAssembler {
    public static ModifyEmployeeCommand toCommand(Long employeeId, ModifyEmployeeResource resource) {
        return new ModifyEmployeeCommand(
                employeeId,
                resource.name(),
                resource.email(),
                resource.role()
        );
    }
}