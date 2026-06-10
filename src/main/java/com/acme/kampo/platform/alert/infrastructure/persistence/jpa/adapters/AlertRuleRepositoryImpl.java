package com.acme.kampo.platform.alert.infrastructure.persistence.jpa.adapters;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.alert.domain.repositories.AlertRuleRepository;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers.AlertRulePersistenceAssembler;
import com.acme.kampo.platform.alert.infrastructure.persistence.jpa.repositories.AlertRuleJpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Adapter implementing the domain {@link AlertRuleRepository} contract using Spring Data JPA.
 * Note: AlertRule creation is a configuration operation — no domain events are published.
 */
@Repository
public class AlertRuleRepositoryImpl implements AlertRuleRepository {

    private final AlertRuleJpaRepository jpaRepository;

    public AlertRuleRepositoryImpl(AlertRuleJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public AlertRule save(AlertRule alertRule) {
        var entity = AlertRulePersistenceAssembler.toPersistenceFromDomain(alertRule);
        var saved  = jpaRepository.save(entity);
        return AlertRulePersistenceAssembler.toDomainFromPersistence(saved);
    }

    @Override
    public Optional<AlertRule> findById(AlertRuleId id) {
        return jpaRepository.findById(id.getValue())
                .map(AlertRulePersistenceAssembler::toDomainFromPersistence);
    }

    @Override
    public List<AlertRule> findByFieldId(FieldId fieldId) {
        return jpaRepository.findByFieldId(fieldId.getValue()).stream()
                .map(AlertRulePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public List<AlertRule> findAll() {
        return jpaRepository.findAll().stream()
                .map(AlertRulePersistenceAssembler::toDomainFromPersistence)
                .toList();
    }

    @Override
    public boolean existsById(AlertRuleId id) {
        return jpaRepository.existsById(id.getValue());
    }
}