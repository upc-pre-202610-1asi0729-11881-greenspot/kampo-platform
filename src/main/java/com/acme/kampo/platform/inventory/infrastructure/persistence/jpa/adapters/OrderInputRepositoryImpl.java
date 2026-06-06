package com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.inventory.domain.model.aggregates.OrderInput;
import com.acme.kampo.platform.inventory.domain.model.enums.OrderStatus;
import com.acme.kampo.platform.inventory.domain.repositories.OrderInputRepository;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.assemblers.OrderInputPersistenceAssembler;
import com.acme.kampo.platform.inventory.infrastructure.persistence.jpa.repositories.OrderInputJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link OrderInputRepository} contract using Spring Data JPA.
 */
@Repository
public class OrderInputRepositoryImpl implements OrderInputRepository {

    private final OrderInputJpaRepository   jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderInputRepositoryImpl(OrderInputJpaRepository jpaRepository,
                                    ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public OrderInput save(OrderInput orderInput) {
        boolean isNew    = orderInput.getId() == null;
        var entity       = OrderInputPersistenceAssembler.toPersistenceFromDomain(orderInput);
        var savedEntity  = jpaRepository.save(entity);
        var savedOrder   = OrderInputPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            savedOrder.domainEvents().forEach(eventPublisher::publishEvent);
            savedOrder.clearDomainEvents();
        }
        return savedOrder;
    }

    @Override
    public Optional<OrderInput> findById(Long id) {
        return jpaRepository.findById(id)
                .map(OrderInputPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<OrderInput> findAll() {
        return jpaRepository.findAll().stream()
                .map(OrderInputPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<OrderInput> findAllByInventoryId(Long inventoryId) {
        return jpaRepository.findAllByInventoryId(inventoryId).stream()
                .map(OrderInputPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<OrderInput> findAllByStatus(OrderStatus status) {
        return jpaRepository.findAllByStatus(status).stream()
                .map(OrderInputPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean hasPendingOrders(Long inventoryId) {
        return jpaRepository.countByInventoryIdAndStatus(inventoryId, OrderStatus.PENDING) > 0;
    }
}