package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for AlertRule persistence entities.
 */
@Repository
public interface AlertRuleJpaRepository extends JpaRepository<AlertRulePersistenceEntity, Long> {

    @Query("select count(r) from AlertRulePersistenceEntity r where r.fieldId = :fieldId and r.readingType = :readingType")
    long countByFieldIdAndReadingType(@Param("fieldId") Long fieldId,
                                      @Param("readingType") String readingType);
}