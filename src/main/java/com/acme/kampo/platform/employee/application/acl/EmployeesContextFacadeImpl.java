package com.acme.kampo.platform.employee.application.acl;

import com.acme.kampo.platform.employee.application.queryservices.EmployeeQueryService;
import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;
import com.acme.kampo.platform.employee.domain.model.queries.GetEmployeeByIdQuery;
import com.acme.kampo.platform.employee.interfaces.acl.EmployeesContextFacade;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeesContextFacadeImpl implements EmployeesContextFacade {

    private final EmployeeQueryService employeeQueryService;

    public EmployeesContextFacadeImpl(EmployeeQueryService employeeQueryService) {
        this.employeeQueryService = employeeQueryService;
    }

    @Override
    public String fetchEmployeeRole(Long employeeId) {
        Optional<Employee> employee = employeeQueryService.handle(
                new GetEmployeeByIdQuery(employeeId)
        );
        return employee.map(e -> e.getRole().name()).orElse("UNKNOWN");
    }
}