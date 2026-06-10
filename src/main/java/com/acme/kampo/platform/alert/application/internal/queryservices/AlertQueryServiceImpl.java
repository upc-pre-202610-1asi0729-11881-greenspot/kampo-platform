package com.acme.kampo.platform.alert.application.internal.queryservices;

import com.acme.kampo.platform.alert.application.queryservices.AlertQueryService;
import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.queries.GetAlertByIdQuery;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertsQuery;
import com.acme.kampo.platform.alert.domain.repositories.AlertRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link AlertQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class AlertQueryServiceImpl implements AlertQueryService {

    private final AlertRepository alertRepository;

    public AlertQueryServiceImpl(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    @Override
    public Optional<Alert> handle(GetAlertByIdQuery query) {
        return alertRepository.findById(query.alertId());
    }

    @Override
    public List<Alert> handle(GetAllAlertsQuery query) {
        return alertRepository.findAll();
    }
}