package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.CropPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for crop persistence entities.
 */
@Repository
public interface CropJpaRepository extends JpaRepository<CropPersistenceEntity, Long> {

    @Query("select c from CropPersistenceEntity c where c.fieldId = :fieldId")
    List<CropPersistenceEntity> findByFieldId(@Param("fieldId") Long fieldId);
}
