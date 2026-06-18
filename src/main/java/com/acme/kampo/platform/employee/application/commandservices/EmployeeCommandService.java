package com.acme.kampo.platform.employee.application.commandservices;

import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;
import com.acme.kampo.platform.employee.domain.model.commands.CreateEmployeeCommand;
import com.acme.kampo.platform.employee.domain.model.commands.DeleteEmployeeCommand;
import com.acme.kampo.platform.employee.domain.model.commands.ModifyEmployeeCommand;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

public interface EmployeeCommandService {
    Result<Employee, ApplicationError> handle(CreateEmployeeCommand command);
    Result<Employee, ApplicationError> handle(ModifyEmployeeCommand command);
    Result<Boolean, ApplicationError> handle(DeleteEmployeeCommand command);
}