package com.acme.kampo.platform.employee.application.internal.commandservices;

import com.acme.kampo.platform.employee.application.commandservices.EmployeeCommandService;
import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;
import com.acme.kampo.platform.employee.domain.model.commands.CreateEmployeeCommand;
import com.acme.kampo.platform.employee.domain.model.commands.DeleteEmployeeCommand;
import com.acme.kampo.platform.employee.domain.model.commands.ModifyEmployeeCommand;
import com.acme.kampo.platform.employee.domain.repositories.EmployeeRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class EmployeeCommandServiceImpl implements EmployeeCommandService {

    private final EmployeeRepository employeeRepository;

    public EmployeeCommandServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Result<Employee, ApplicationError> handle(CreateEmployeeCommand command) {
        try {
            Employee employee = Employee.create(command);
            Employee saved = employeeRepository.save(employee);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("CreateEmployee", e.getMessage()));
        }
    }

    @Override
    public Result<Employee, ApplicationError> handle(ModifyEmployeeCommand command) {
        try {
            Optional<Employee> existing = employeeRepository.findById(command.employeeId());
            if (existing.isEmpty())
                return Result.failure(ApplicationError.notFound("Employee", command.employeeId().toString()));
            Employee employee = existing.get();
            employee.modifyEmployee(command);
            Employee saved = employeeRepository.save(employee);
            return Result.success(saved);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("ModifyEmployee", e.getMessage()));
        }
    }

    @Override
    public Result<Boolean, ApplicationError> handle(DeleteEmployeeCommand command) {
        try {
            Optional<Employee> existing = employeeRepository.findById(command.employeeId());
            if (existing.isEmpty())
                return Result.failure(ApplicationError.notFound("Employee", command.employeeId().toString()));
            employeeRepository.delete(command.employeeId());
            return Result.success(true);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("DeleteEmployee", e.getMessage()));
        }
    }
}