package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.repositories.AlertRuleRepository;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers.AlertRulePersistenceAssembler;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.repositories.AlertRuleJpaRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link AlertRuleRepository} contract using Spring Data JPA.
 */
@Repository
public class AlertRuleRepositoryImpl implements AlertRuleRepository {

    private final AlertRuleJpaRepository jpaRepository;
    private final ApplicationEventPublisher eventPublisher;

    public AlertRuleRepositoryImpl(AlertRuleJpaRepository jpaRepository,
                                   ApplicationEventPublisher eventPublisher) {
        this.jpaRepository = jpaRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public AlertRule save(AlertRule alertRule) {
        boolean isNew = alertRule.getId() == null;
        var entity = AlertRulePersistenceAssembler.toPersistenceFromDomain(alertRule);
        var savedEntity = jpaRepository.save(entity);
        var savedAlertRule = AlertRulePersistenceAssembler.toDomainFromPersistence(savedEntity);
        if (isNew) {
            alertRule.reconstitute(savedEntity.getId());
            alertRule.domainEvents().forEach(eventPublisher::publishEvent);
            alertRule.clearDomainEvents();
        }
        return savedAlertRule;
    }

    @Override
    public Optional<AlertRule> findById(Long id) {
        return jpaRepository.findById(id)
                .map(AlertRulePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<AlertRule> findAll() {
        return jpaRepository.findAll().stream()
                .map(AlertRulePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsByFieldIdAndReadingType(Long fieldId, String readingType) {
        return jpaRepository.countByFieldIdAndReadingType(fieldId, readingType) > 0;
    }
}