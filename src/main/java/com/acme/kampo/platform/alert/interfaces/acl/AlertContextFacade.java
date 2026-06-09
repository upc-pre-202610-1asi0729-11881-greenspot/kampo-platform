package com.acme.kampo.platform.alert.interfaces.acl;

import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;

import java.util.Optional;

/**
 * Anti-Corruption Layer facade exposing the Alert bounded context
 * to other bounded contexts.
 */
public interface AlertContextFacade {
    Long createAlertRule(ReadingType readingType, ConditionOperator conditionOperator,
                         SeverityLevel severity, Long fieldId);
    Optional<Long> fetchAlertRuleIdByFieldId(Long fieldId);
}