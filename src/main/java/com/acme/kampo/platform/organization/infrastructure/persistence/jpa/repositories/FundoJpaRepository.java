package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.FundoPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for fundo persistence entities.
 */
@Repository
public interface FundoJpaRepository extends JpaRepository<FundoPersistenceEntity, Long> {

    @Query("select f from FundoPersistenceEntity f where f.organizationId = :organizationId")
    List<FundoPersistenceEntity> findByOrganizationId(@Param("organizationId") Long organizationId);
}