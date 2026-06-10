package com.acme.kampo.platform.alert.domain.model.aggregates;

import com.acme.kampo.platform.alert.domain.model.commands.ConfigureAlertRuleCommand;
import com.acme.kampo.platform.alert.domain.model.enums.ConditionOperator;
import com.acme.kampo.platform.alert.domain.model.enums.ReadingType;
import com.acme.kampo.platform.alert.domain.model.enums.SeverityLevel;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root representing a configured alert rule for a field.
 *
 * <p>An alert rule defines:
 * <ul>
 *   <li>What to monitor ({@link ReadingType}).</li>
 *   <li>When to trigger ({@link ConditionOperator} against a threshold).</li>
 *   <li>How serious it is ({@link SeverityLevel}).</li>
 *   <li>Which field it applies to ({@link FieldId}).</li>
 * </ul>
 *
 * <p>The evaluation logic lives in {@link #evaluate(double)} — the aggregate
 * delegates to {@link ConditionOperator#evaluate(double, double)}, keeping
 * the decision-making encapsulated in the domain.
 */
@Getter
public class AlertRule extends AbstractDomainAggregateRoot<AlertRule> {

    private AlertRuleId       id;
    private ReadingType       readingType;
    private ConditionOperator conditionOperator;
    private Double            threshold;
    private SeverityLevel     severity;
    private FieldId           fieldId;

    /** Required by JPA proxy — do not use directly. */
    protected AlertRule() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     * Used exclusively by the persistence assembler.
     */
    public AlertRule(Long id, ReadingType readingType, ConditionOperator conditionOperator,
                     Double threshold, SeverityLevel severity, Long fieldId) {
        this.id                = AlertRuleId.of(id);
        this.readingType       = readingType;
        this.conditionOperator = conditionOperator;
        this.threshold         = threshold;
        this.severity          = severity;
        this.fieldId           = FieldId.of(fieldId);
    }

    /**
     * Creation constructor — builds from a {@link ConfigureAlertRuleCommand}.
     * No domain event is published — alert rule creation is a configuration
     * operation, not a domain-significant event.
     *
     * @param command the configuration command
     */
    public AlertRule(ConfigureAlertRuleCommand command) {
        this.readingType       = command.readingType();
        this.conditionOperator = command.conditionOperator();
        this.threshold         = command.threshold();
        this.severity          = command.severity();
        this.fieldId           = FieldId.of(command.fieldId());
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Evaluates whether a sensor reading violates this rule's condition.
     *
     * <p>Delegates the comparison to {@link ConditionOperator#evaluate(double, double)},
     * keeping the conditional logic encapsulated in the enum.</p>
     *
     * @param currentValue the current sensor reading to check
     * @return {@code true} if the condition is met and an alert should be sent
     */
    public boolean evaluate(double currentValue) {
        return conditionOperator.evaluate(currentValue, threshold);
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public AlertRule reconstitute(Long rawId) {
        this.id = AlertRuleId.of(rawId);
        return this;
    }
}