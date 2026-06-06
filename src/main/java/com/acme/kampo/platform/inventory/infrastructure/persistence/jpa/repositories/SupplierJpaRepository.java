package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Spring Data repository for supplier persistence entities.
 */
@Repository
public interface SupplierJpaRepository extends JpaRepository<SupplierPersistenceEntity, Long> {

    @Query("select s from SupplierPersistenceEntity s where s.email = :email")
    Optional<SupplierPersistenceEntity> findByEmail(@Param("email") String email);

    @Query("select count(s) from SupplierPersistenceEntity s where s.email = :email")
    long countByEmail(@Param("email") String email);
}

