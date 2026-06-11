package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.OrganizationPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for organization persistence entities.
 */
@Repository
public interface OrganizationJpaRepository extends JpaRepository<OrganizationPersistenceEntity, Long> {

    @Query("select o from OrganizationPersistenceEntity o where o.name = :name")
    Optional<OrganizationPersistenceEntity> findByName(@Param("name") String name);

    @Query("select count(o) from OrganizationPersistenceEntity o where o.name = :name")
    long countByName(@Param("name") String name);
}