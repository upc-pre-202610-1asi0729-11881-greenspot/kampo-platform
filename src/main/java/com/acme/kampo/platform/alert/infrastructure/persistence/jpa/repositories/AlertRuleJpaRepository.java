package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities.AlertRulePersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for alert rule persistence entities.
 */
@Repository
public interface AlertRuleJpaRepository extends JpaRepository<AlertRulePersistenceEntity, Long> {

    @Query("select r from AlertRulePersistenceEntity r where r.fieldId = :fieldId")
    List<AlertRulePersistenceEntity> findByFieldId(@Param("fieldId") Long fieldId);
}