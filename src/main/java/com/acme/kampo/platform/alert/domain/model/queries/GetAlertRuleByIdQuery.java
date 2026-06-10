package com.acme.kampo.platform.alert.domain.model.queries;

/**
 * Query to retrieve a single AlertRule by its ID.
 */
public record GetAlertRuleByIdQuery(Long alertRuleId) {
    public GetAlertRuleByIdQuery {
        if (alertRuleId == null || alertRuleId <= 0)
            throw new IllegalArgumentException("alertRuleId must be positive");
    }
}