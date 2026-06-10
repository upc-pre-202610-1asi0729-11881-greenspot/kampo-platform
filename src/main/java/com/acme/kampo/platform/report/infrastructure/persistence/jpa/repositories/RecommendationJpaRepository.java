package com.acme.kampo.platform.report.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.report.infrastructure.persistence.jpa.entities.RecommendationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecommendationJpaRepository extends JpaRepository<RecommendationPersistenceEntity, Long> {

    List<RecommendationPersistenceEntity> findAllByReportId(Long reportId);
}
