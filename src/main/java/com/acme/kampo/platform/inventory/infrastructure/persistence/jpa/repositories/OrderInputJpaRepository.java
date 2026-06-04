package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories;

import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.OrderInputPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for {@link OrderInputPersistenceEntity}.
 */
public interface OrderInputJpaRepository extends JpaRepository<OrderInputPersistenceEntity, Long> {
}
