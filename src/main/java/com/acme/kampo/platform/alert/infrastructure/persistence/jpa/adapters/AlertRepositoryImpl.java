package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.alert.domain.repositories.AlertRepository;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers.AlertPersistenceAssembler;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.repositories.AlertJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link AlertRepository} contract using Spring Data JPA.
 * Publishes domain events after a new Alert is persisted.
 */
@Repository
public class AlertRepositoryImpl implements AlertRepository {

    private final AlertJpaRepository        jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AlertRepositoryImpl(AlertJpaRepository jpaRepository,
                               ApplicationEventPublisher eventPublisher) {
        this.jpaRepository  = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Alert save(Alert alert) {
        boolean isNew  = alert.getId() == null;
        var entity     = AlertPersistenceAssembler.toPersistenceFromDomain(alert);
        var saved      = jpaRepository.save(entity);
        var savedAlert = AlertPersistenceAssembler.toDomainFromPersistence(saved);
        if (isNew) {
            savedAlert.domainEvents().forEach(eventPublisher::publishEvent);
            savedAlert.clearDomainEvents();
        }
        return savedAlert;
    }

    @Override
    public Optional<Alert> findById(AlertId id) {
        return jpaRepository.findById(id.getValue())
                .map(AlertPersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<Alert> findByFieldId(FieldId fieldId) {
        return jpaRepository.findByFieldId(fieldId.getValue()).stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Alert> findAllUnread() {
        return jpaRepository.findAllUnread().stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<Alert> findAll() {
        return jpaRepository.findAll().stream()
                .map(AlertPersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(AlertId id) {
        return jpaRepository.existsById(id.getValue());
    }
}