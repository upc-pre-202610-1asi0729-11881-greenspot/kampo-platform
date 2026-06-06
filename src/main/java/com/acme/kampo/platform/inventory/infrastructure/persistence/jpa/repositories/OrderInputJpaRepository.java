package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.OrderInputPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Spring Data repository for order input persistence entities.
 */
@Repository
public interface OrderInputJpaRepository extends JpaRepository<OrderInputPersistenceEntity, Long> {

    @Query("select o from OrderInputPersistenceEntity o where o.inventoryId = :inventoryId")
    List<OrderInputPersistenceEntity> findAllByInventoryId(@Param("inventoryId") Long inventoryId);

    @Query("select o from OrderInputPersistenceEntity o where o.status = :status")
    List<OrderInputPersistenceEntity> findAllByStatus(@Param("status") OrderStatus status);

    @Query("select count(o) from OrderInputPersistenceEntity o where o.inventoryId = :inventoryId and o.status = :status")
    long countByInventoryIdAndStatus(@Param("inventoryId") Long inventoryId, @Param("status") OrderStatus status);
}
