package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.SupplierPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link SupplierPersistenceEntity}.
 */
public interface SupplierJpaRepository extends JpaRepository<SupplierPersistenceEntity, Long> {
}
