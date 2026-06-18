package com.acme.kampo.platform.employee.application.queryservices;

import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;
import com.acme.kampo.platform.employee.domain.model.queries.GetEmployeeByIdQuery;

import java.util.Optional;

public interface EmployeeQueryService {
    Optional<Employee> handle(GetEmployeeByIdQuery query);
}