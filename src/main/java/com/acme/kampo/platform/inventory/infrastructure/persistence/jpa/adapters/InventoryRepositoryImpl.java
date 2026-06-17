package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.model.enums.InventoryStatus;
import com.acme.kampo.platform.inventory.domain.repositories.InventoryRepository;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.InventoryPersistenceAssembler;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.InventoryPersistenceEntity;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link InventoryRepository} contract using Spring Data JPA.
 *
 * <p>Acts as the event-publishing boundary: after a brand-new {@link Inventory} is
 * persisted (and the JPA-assigned id is available), an {@link InventoryCreatedEvent}
 * is dispatched via Spring's {@link ApplicationEventPublisher}.
 */

/**
 * Adapter implementing the domain {@link InventoryRepository} contract using Spring Data JPA.
 */
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {

    private final InventoryJpaRepository    jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public InventoryRepositoryImpl(InventoryJpaRepository jpaRepository,
                                   ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Inventory save(Inventory inventory) {
        boolean isNew      = inventory.getId() == null;
        var entity         = InventoryPersistenceAssembler.toPersistenceFromDomain(inventory);
        var savedEntity    = jpaRepository.save(entity);
        var savedInventory = InventoryPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedInventory.domainEvents().forEach(eventPublisher::publishEvent);
            savedInventory.clearDomainEvents();
        }
        return savedInventory;
    }

    @Override
    public Optional<Inventory> findById(Long id) {
        return jpaRepository.findById(id)
                .map(InventoryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public Optional<Inventory> findByName(String name) {
        return jpaRepository.findByName(name)
                .map(InventoryPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Inventory> findAll() {
        return jpaRepository.findAll().stream()
                .map(InventoryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Inventory> findAllByStatus(InventoryStatus status) {
        return jpaRepository.findAllByStatus(status).stream()
                .map(InventoryPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepository.countByName(name) > 0;
    }
}
