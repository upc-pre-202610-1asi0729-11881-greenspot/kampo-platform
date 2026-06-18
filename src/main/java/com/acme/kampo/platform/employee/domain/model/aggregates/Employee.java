package com.acme.kampo.platform.employee.domain.model.aggregates;

import com.acme.kampo.platform.employee.domain.model.commands.CreateEmployeeCommand;
import com.acme.kampo.platform.employee.domain.model.commands.ModifyEmployeeCommand;
import com.acme.kampo.platform.employee.domain.model.enums.EmployeeRole;
import com.acme.kampo.platform.employee.domain.model.enums.EmployeeStatus;
import com.acme.kampo.platform.employee.domain.model.events.EmployeeCreatedEvent;
import com.acme.kampo.platform.employee.domain.model.valueobjects.Email;
import com.acme.kampo.platform.employee.domain.model.valueobjects.EmployeeId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDateTime;

public class Employee extends AbstractDomainAggregateRoot<Employee> {
    private EmployeeId id;
    private String name;
    private Email email;
    private EmployeeRole role;
    private EmployeeStatus status;
    private Long fieldId;
    private LocalDateTime createdAt;

    public Employee(){}

    public static Employee create(CreateEmployeeCommand command){
        Employee employee = new Employee();
        employee.id = EmployeeId.of(System.currentTimeMillis());
        employee.name = command.name();
        employee.email = Email.of(command.email());
        employee.role = EmployeeRole.valueOf(command.role());
        employee.status = EmployeeStatus.ACTIVE;
        employee.fieldId = command.fieldId();
        employee.createdAt = LocalDateTime.now();
        employee.registerDomainEvent(new EmployeeCreatedEvent(employee.id.getValue(), employee.name));
        return employee;
    }

    public void modifyEmployee(ModifyEmployeeCommand command){
        this.name = command.name();
        this.email = Email.of(command.email());
        this.role = EmployeeRole.valueOf(command.role());
    }

    public void active(){
        this.status = EmployeeStatus.ACTIVE;
    }

    public void deactivate() {
        this.status = EmployeeStatus.INACTIVE;
    }

    public EmployeeId getId() { return id; }
    public String getName() { return name; }
    public Email getEmail() { return email; }
    public EmployeeRole getRole() { return role; }
    public EmployeeStatus getStatus() { return status; }
    public Long getFieldId() { return fieldId; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    public static Employee reconstitute(
            Long id,
            String name,
            String email,
            EmployeeRole role,
            EmployeeStatus status,
            Long fieldId,
            LocalDateTime createdAt
    ) {
        Employee employee = new Employee();
        employee.id = EmployeeId.of(id);
        employee.name = name;
        employee.email = Email.of(email);
        employee.role = role;
        employee.status = status;
        employee.fieldId = fieldId;
        employee.createdAt = createdAt;
        return employee;
    }

}
