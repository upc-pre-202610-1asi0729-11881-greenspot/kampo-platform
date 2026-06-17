package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.InventoryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data repository for inventory persistence entities.
 */
@Repository
public interface InventoryJpaRepository extends JpaRepository<InventoryPersistenceEntity, Long> {

    @Query("select i from InventoryPersistenceEntity i where i.name = :name")
    Optional<InventoryPersistenceEntity> findByName(@Param("name") String name);

    @Query("select count(i) from InventoryPersistenceEntity i where i.name = :name")
    long countByName(@Param("name") String name);

    @Query("select i from InventoryPersistenceEntity i where i.status = :status")
    List<InventoryPersistenceEntity> findAllByStatus(@Param("status") InventoryStatus status);
}

