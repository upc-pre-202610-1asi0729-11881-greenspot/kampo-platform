package com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.profileaccess.domain.model.enums.PermissionCategory;
import com.acme.kampo.platform.profileaccess.infrastructure.persistence.jpa.entities.PermissionPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionJpaRepository extends JpaRepository<PermissionPersistenceEntity, Long> {

    @Query("select p from PermissionPersistenceEntity p where p.category = :category")
    Optional<PermissionPersistenceEntity> findByCategory(@Param("category") PermissionCategory category);

    @Query("select count(p) from PermissionPersistenceEntity p where p.category = :category")
    long countByCategory(@Param("category") PermissionCategory category);
}