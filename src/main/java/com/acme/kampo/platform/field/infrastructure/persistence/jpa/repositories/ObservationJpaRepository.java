package com.acme.kampo.platform.field.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities.ObservationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for observation persistence entities.
 */
@Repository
public interface ObservationJpaRepository extends JpaRepository<ObservationPersistenceEntity, Long> {

    @Query("select o from ObservationPersistenceEntity o where o.fieldVisitId = :fieldVisitId")
    List<ObservationPersistenceEntity> findByFieldVisitId(@Param("fieldVisitId") Long fieldVisitId);
}