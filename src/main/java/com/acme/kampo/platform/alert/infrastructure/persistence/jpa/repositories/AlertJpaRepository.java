package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.entities.AlertPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data JPA repository for Alert persistence entities.
 */
@Repository
public interface AlertJpaRepository extends JpaRepository<AlertPersistenceEntity, Long> {

    @Query("select a from AlertPersistenceEntity a where a.fieldId = :fieldId")
    List<AlertPersistenceEntity> findAllByFieldId(@Param("fieldId") Long fieldId);

    @Query("select a from AlertPersistenceEntity a where a.isRead = false")
    List<AlertPersistenceEntity> findAllUnread();
}