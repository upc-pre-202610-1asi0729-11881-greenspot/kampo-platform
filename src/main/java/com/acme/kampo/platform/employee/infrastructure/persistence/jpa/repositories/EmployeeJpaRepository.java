package com.acme.kampo.platform.employee.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.employee.infrastructure.persistence.jpa.entities.EmployeePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeJpaRepository extends JpaRepository<EmployeePersistenceEntity, Long> {
    Optional<EmployeePersistenceEntity> findByEmail(String email);
}