package com.acme.kampo.platform.alert.domain.repositories;

import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link AlertRule} aggregate.
 */
public interface AlertRuleRepository {
    AlertRule save(AlertRule alertRule);
    Optional<AlertRule> findById(Long id);
    List<AlertRule> findAll();
    boolean existsByFieldIdAndReadingType(Long fieldId, String readingType);
}