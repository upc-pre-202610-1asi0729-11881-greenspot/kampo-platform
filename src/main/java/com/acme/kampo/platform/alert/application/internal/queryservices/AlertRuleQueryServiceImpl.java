package com.acme.kampo.platform.alert.application.internal.queryservices;

import com.acme.kampo.platform.alert.application.queryservices.AlertRuleQueryService;
import com.acme.kampo.platform.alert.domain.model.aggregates.AlertRule;
import com.acme.kampo.platform.alert.domain.model.queries.GetAlertRuleByIdQuery;
import com.acme.kampo.platform.alert.domain.model.queries.GetAllAlertRulesQuery;
import com.acme.kampo.platform.alert.domain.repositories.AlertRuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link AlertRuleQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class AlertRuleQueryServiceImpl implements AlertRuleQueryService {

    private final AlertRuleRepository alertRuleRepository;

    public AlertRuleQueryServiceImpl(AlertRuleRepository alertRuleRepository) {
        this.alertRuleRepository = alertRuleRepository;
    }

    @Override
    public Optional<AlertRule> handle(GetAlertRuleByIdQuery query) {
        return alertRuleRepository.findById(query.alertRuleId());
    }

    @Override
    public List<AlertRule> handle(GetAllAlertRulesQuery query) {
        return alertRuleRepository.findAll();
    }
}