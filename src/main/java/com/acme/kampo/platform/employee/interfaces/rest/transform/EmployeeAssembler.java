package com.acme.kampo.platform.employee.interfaces.rest.transform;

import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;
import com.acme.kampo.platform.employee.interfaces.rest.resources.EmployeeResource;

public class EmployeeAssembler {
    public static EmployeeResource toResource(Employee employee) {
        return new EmployeeResource(
                employee.getId().getValue(),
                employee.getName(),
                employee.getEmail().getValue(),
                employee.getRole().name(),
                employee.getStatus().name(),
                employee.getFieldId()
        );
    }
}