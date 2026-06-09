package com.acme.kampo.platform.alert.domain.repositories;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;

import java.util.List;
import java.util.Optional;

/**
 * Domain repository contract for the {@link Alert} aggregate.
 */
public interface AlertRepository {
    Alert save(Alert alert);
    Optional<Alert> findById(Long id);
    List<Alert> findAll();
    List<Alert> findAllByFieldId(Long fieldId);
    List<Alert> findAllUnread();
}