package com.acme.kampo.platform.alert.application.queryservices;

import com.acme.kampo.platform.alert.domain.model.aggregates.Alert;
import com.acme.kampo.platform.alert.domain.model.queries.GetAlertByIdQuery;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertsQuery;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for Alert read operations.
 */
public interface AlertQueryService {
    Optional<Alert> handle(GetAlertByIdQuery query);
    List<Alert> handle(GetAllAlertsQuery query);
}