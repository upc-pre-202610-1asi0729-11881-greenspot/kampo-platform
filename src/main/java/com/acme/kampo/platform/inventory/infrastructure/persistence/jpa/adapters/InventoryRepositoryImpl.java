package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.inventory.domain.model.aggregates.Inventory;
import com.acme.kampo.platform.inventory.domain.repositories.InventoryRepository;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.InventoryPersistenceEntity;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories.InventoryJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link InventoryRepository} contract
 * using Spring Data JPA as the underlying persistence mechanism.
 *
 * <p>Responsibilities:
 * <ul>
 *   <li>Translates {@link Inventory} aggregates to {@link InventoryPersistenceEntity} before saving.</li>
 *   <li>Translates {@link InventoryPersistenceEntity} back to {@link Inventory} after loading.</li>
 *   <li>Calls {@code reconstitute()} after saving so the returned aggregate has its assigned ID.</li>
 *   <li>Publishes domain events accumulated in the aggregate after saving via
 *       Spring Data's {@link org.springframework.data.domain.AbstractAggregateRoot} mechanism.</li>
 * </ul>
 */
@Repository
public class InventoryRepositoryImpl implements InventoryRepository {
    private final InventoryJpaRepository jpaRepository;
    public InventoryRepositoryImpl(InventoryJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    /**
     * Persists the aggregate:
     * <ol>
     *   <li>Converts the aggregate to a persistence entity.</li>
     *   <li>Saves via JPA — Spring Data fires any registered domain events here.</li>
     *   <li>Binds the database-assigned ID back into the aggregate via {@code reconstitute()}.</li>
     * </ol>
     */
    @Override
    public Inventory save(Inventory inventory) {
        var entity = InventoryPersistenceEntity.fromDomainModel(inventory);
        var saved  = jpaRepository.save(entity);
        return inventory.reconstitute(saved.getId());
    }
    @Override
    public Optional<Inventory> findById(Long id) {
        return jpaRepository.findById(id)
                .map(InventoryPersistenceEntity::toDomainModel);
    }
    @Override
    public List<Inventory> findAll() {
        return jpaRepository.findAll().stream()
                .map(InventoryPersistenceEntity::toDomainModel)
                .toList();
    }
}