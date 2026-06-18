package com.acme.kampo.platform.employee.infrastructure.persistence.jpa.entities;


import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;
import com.acme.kampo.platform.employee.domain.model.enums.EmployeeRole;
import com.acme.kampo.platform.employee.domain.model.enums.EmployeeStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class EmployeePersistenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeRole role;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EmployeeStatus status;

    @Column(nullable = false)
    private Long fieldId;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    public EmployeePersistenceEntity(){}

    public EmployeePersistenceEntity(Employee employee){
        this.id = employee.getId() != null ? employee.getId().getValue() : null;
        this.name = employee.getName();
        this.email = employee.getEmail().getValue();
        this.role = employee.getRole();
        this.status = employee.getStatus();
        this.fieldId = employee.getFieldId();
        this.createdAt = employee.getCreatedAt();
    }

    public Employee toDomainModel() {
        return Employee.reconstitute(
                this.id,
                this.name,
                this.email,
                this.role,
                this.status,
                this.fieldId,
                this.createdAt
        );
    }

}
