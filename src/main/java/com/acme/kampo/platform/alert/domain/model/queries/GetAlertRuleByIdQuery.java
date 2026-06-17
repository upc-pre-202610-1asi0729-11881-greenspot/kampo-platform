package com.acme.kampo.platform.alert.domain.model.queries;

import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;

/**
 * Query to retrieve a single AlertRule by its typed identity.
 *
 * @param alertRuleId the alert rule identity
 */
public record GetAlertRuleByIdQuery(AlertRuleId alertRuleId) {
    public GetAlertRuleByIdQuery {
        if (alertRuleId == null) throw new IllegalArgumentException("alertRuleId must not be null");
    }
}