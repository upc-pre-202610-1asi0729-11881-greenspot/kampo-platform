package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.repositories.OrderInputRepository;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.entities.OrderInputPersistenceEntity;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories.OrderInputJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter that implements the domain {@link OrderInputRepository} contract
 * using Spring Data JPA.
 */
@Repository
public class OrderInputRepositoryImpl implements OrderInputRepository {

    private final OrderInputJpaRepository jpaRepository;

    public OrderInputRepositoryImpl(OrderInputJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public OrderInput save(OrderInput orderInput) {
        var entity = OrderInputPersistenceEntity.fromDomainModel(orderInput);
        var saved  = jpaRepository.save(entity);
        return orderInput.reconstitute(saved.getId());
    }

    @Override
    public Optional<OrderInput> findById(Long id) {
        return jpaRepository.findById(id)
                .map(OrderInputPersistenceEntity::toDomainModel);
    }

    @Override
    public List<OrderInput> findAll() {
        return jpaRepository.findAll().stream()
                .map(OrderInputPersistenceEntity::toDomainModel)
                .toList();
    }
}
