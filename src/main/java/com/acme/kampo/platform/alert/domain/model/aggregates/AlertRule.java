package com.acme.kampo.platform.alert.domain.model.aggregates;

import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.domain.model.commands.EvaluateConditionCommand;
import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;
import com.acme.kampo.platform.alert.domain.model.events.AlertRuleCreatedEvent;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root that represents an alert rule configuration.
 *
 * <p>Responsible for:
 * <ul>
 *   <li>Defining the condition that triggers an alert.</li>
 *   <li>Evaluating whether a reading meets the configured condition.</li>
 *   <li>Publishing {@link AlertRuleCreatedEvent} upon creation.</li>
 * </ul>
 */
@Getter
public class AlertRule extends AbstractDomainAggregateRoot<AlertRule> {

    private AlertRuleId id;
    private ReadingType readingType;
    private ConditionOperator conditionOperator;
    private SeverityLevel severity;
    private FieldId fieldId;

    /** Required by JPA proxy — do not use directly. */
    protected AlertRule() {}

    /**
     * Reconstitution constructor — rebuilds the aggregate from persisted values
     * without triggering domain logic or events.
     * Used exclusively by {@link com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers.AlertRulePersistenceAssembler}.
     */
    public AlertRule(Long id, ReadingType readingType, ConditionOperator conditionOperator,
                     SeverityLevel severity, Long fieldId) {
        this.id = AlertRuleId.of(id);
        this.readingType = readingType;
        this.conditionOperator = conditionOperator;
        this.severity = severity;
        this.fieldId = FieldId.of(fieldId);
    }

    /**
     * Creates a new AlertRule from a {@link ConfigureAlertRuleCommand}.
     * Registers an {@link AlertRuleCreatedEvent} to be published after save.
     */
    public AlertRule(ConfigureAlertRuleCommand command) {
        this.readingType = command.readingType();
        this.conditionOperator = command.conditionOperator();
        this.severity = command.severity();
        this.fieldId = FieldId.of(command.fieldId());
        registerDomainEvent(new AlertRuleCreatedEvent(this));
    }

    /**
     * Reconstitutes an AlertRule from persistence, binding its typed identity.
     *
     * @param rawId the surrogate key assigned by the database
     * @return this instance (fluent)
     */
    public AlertRule reconstitute(Long rawId) {
        this.id = AlertRuleId.of(rawId);
        return this;
    }

    /**
     * Evaluates whether the condition of this rule is currently met.
     * Placeholder — real evaluation logic depends on sensor reading integration.
     *
     * @param command the evaluate condition command
     * @return true if the condition is met
     */
    public boolean evaluate(EvaluateConditionCommand command) {
        return true;
    }
}