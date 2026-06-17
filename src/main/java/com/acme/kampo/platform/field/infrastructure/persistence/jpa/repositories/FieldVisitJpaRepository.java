package com.acme.kampo.platform.field.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.field.infrastructure.persistence.jpa.entities.FieldVisitPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for field visit persistence entities.
 */
@Repository
public interface FieldVisitJpaRepository extends JpaRepository<FieldVisitPersistenceEntity, Long> {

    @Query("select fv from FieldVisitPersistenceEntity fv " +
            "where fv.fieldId = :fieldId " +
            "order by fv.scheduledAt desc")
    List<FieldVisitPersistenceEntity> findByFieldId(@Param("fieldId") Long fieldId);
}