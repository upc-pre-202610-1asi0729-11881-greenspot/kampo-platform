package com.acme.kampo.platform.report.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.report.infrastructure.persistence.jpa.entities.ReportPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReportJpaRepository extends JpaRepository<ReportPersistenceEntity, Long> {
}
