package com.acme.kampo.platform.alert.domain.model.queries;

import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertId;

/**
 * Query to retrieve a single Alert by its typed identity.
 *
 * @param alertId the alert identity
 */
public record GetAlertByIdQuery(AlertId alertId) {
    public GetAlertByIdQuery {
        if (alertId == null) throw new IllegalArgumentException("alertId must not be null");
    }
}