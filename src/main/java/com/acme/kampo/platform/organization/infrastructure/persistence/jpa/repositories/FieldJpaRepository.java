package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.FieldPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for field persistence entities.
 */
@Repository
public interface FieldJpaRepository extends JpaRepository<FieldPersistenceEntity, Long> {

    @Query("select f from FieldPersistenceEntity f where f.fundoId = :fundoId")
    List<FieldPersistenceEntity> findByFundoId(@Param("fundoId") Long fundoId);
}
