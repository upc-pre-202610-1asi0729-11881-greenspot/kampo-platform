package com.acme.kampo.platform.employee.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;
import com.acme.kampo.platform.employee.domain.repositories.EmployeeRepository;
import com.acme.kampo.platform.employee.infrastructure.persistence.jpa.entities.EmployeePersistenceEntity;
import com.acme.kampo.platform.employee.infrastructure.persistence.jpa.repositories.EmployeeJpaRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class EmployeeRepositoryImpl implements EmployeeRepository {

    private final EmployeeJpaRepository employeeJpaRepository;

    public EmployeeRepositoryImpl(EmployeeJpaRepository employeeJpaRepository) {
        this.employeeJpaRepository = employeeJpaRepository;
    }

    @Override
    public Optional<Employee> findById(Long id) {
        return employeeJpaRepository.findById(id)
                .map(entity -> ((EmployeePersistenceEntity) entity).toDomainModel());
    }

    @Override
    public List<Employee> findAll() {
        return employeeJpaRepository.findAll()
                .stream()
                .map(entity -> ((EmployeePersistenceEntity) entity).toDomainModel())
                .toList();
    }

    @Override
    public Employee save(Employee employee) {
        EmployeePersistenceEntity entity = new EmployeePersistenceEntity(employee);
        EmployeePersistenceEntity saved = employeeJpaRepository.save(entity);
        return saved.toDomainModel();
    }

    @Override
    public void delete(Long id) {
        employeeJpaRepository.deleteById(id);
    }
}