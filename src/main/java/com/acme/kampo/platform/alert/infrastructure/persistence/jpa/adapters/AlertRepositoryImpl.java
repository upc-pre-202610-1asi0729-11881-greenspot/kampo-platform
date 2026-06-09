package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.repositories.AlertRepository;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers.AlertPersistenceAssembler;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.repositories.AlertJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link AlertRepository} contract using Spring Data JPA.
 */
@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final AlertJpaRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AlertRepositoryImpl(AlertJpaRepository jpaRepository,
                               ApplicationEventPublisher eventPublisher) {
        this.jpaRepository = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Alert save(Alert alert) {
        boolean isNew = alert.getId() == null;
        var entity = AlertPersistenceAssembler.toPersistenceFromDomain(alert);
        var savedEntity = jpaRepository.save(entity);
        var savedAlert = AlertPersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            alert.reconstitute(savedEntity.getId());
            alert.domainEvents().forEach(eventPublisher::publishEvent);
            alert.clearDomainEvents();
        }
        return savedAlert;
    }

    @Override
    public Optional<Alert> findById(Long id) {
        return jpaRepository.findById(id)
                .map(AlertPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Alert> findAll() {
        return jpaRepository.findAll().stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Alert> findAllByFieldId(Long fieldId) {
        return jpaRepository.findAllByFieldId(fieldId).stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Alert> findAllUnread() {
        return jpaRepository.findAllUnread().stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }
}