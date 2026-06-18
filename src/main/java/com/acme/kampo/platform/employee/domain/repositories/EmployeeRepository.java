package com.acme.kampo.platform.employee.domain.repositories;

import com.acme.kampo.platform.employee.domain.model.aggregates.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    Optional<Employee> findById(Long id);
    List <Employee> findAll();
    Employee save(Employee employee);
    void delete(Long id);
}
