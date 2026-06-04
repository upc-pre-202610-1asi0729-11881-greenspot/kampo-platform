package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.InventoryPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link InventoryPersistenceEntity}.
 *
 * <p>Works exclusively with persistence entities — never with domain aggregates.
 * The domain adapter ({@code InventoryRepositoryImpl}) translates between the two.
 */
public interface InventoryJpaRepository extends JpaRepository<InventoryPersistenceEntity, Long> {
}
