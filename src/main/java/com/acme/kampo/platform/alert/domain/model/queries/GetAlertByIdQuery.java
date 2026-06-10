package com.acme.kampo.platform.alert.domain.model.queries;

/**
 * Query to retrieve a single Alert by its ID.
 */
public record GetAlertByIdQuery(Long alertId) {
    public GetAlertByIdQuery {
        if (alertId == null || alertId <= 0)
            throw new IllegalArgumentException("alertId must be positive");
    }
}