package com.acme.kampo.platform.alert.domain.model.aggregates;

import com.acme.kampo.platform.alert.domain.model.commands.MarkAlertReadCommand;
import com.acme.kampo.platform.alert.domain.model.commands.SendAlertCommand;
import com.acme.kampo.platform.alert.domain.model.enums.AlertPriority;
import com.acme.kampo.platform.alert.domain.model.events.AlertSentEvent;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.AlertRuleId;
import com.acme.kampo.platform.alert.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root that represents a triggered alert.
 *
 * <p>Responsible for:
 * <ul>
 *   <li>Tracking the alert message, priority and read state.</li>
 *   <li>Publishing {@link AlertSentEvent} upon creation.</li>
 * </ul>
 */
@Getter
public class Alert extends AbstractDomainAggregateRoot<Alert> {

    private AlertId id;
    private String message;
    private AlertPriority priority;
    private boolean isRead;
    private FieldId fieldId;
    private AlertRuleId alertRuleId;

    /** Required by JPA proxy — do not use directly. */
    protected Alert() {}

    /**
     * Reconstitution constructor — rebuilds the aggregate from persisted values
     * without triggering domain logic or events.
     * Used exclusively by {@link com.acme.kampo.platform.alert.infrastructure.persistence.jpa.assemblers.AlertPersistenceAssembler}.
     */
    public Alert(Long id, String message, AlertPriority priority,
                 boolean isRead, Long fieldId, Long alertRuleId) {
        this.id = AlertId.of(id);
        this.message = message;
        this.priority = priority;
        this.isRead = isRead;
        this.fieldId = FieldId.of(fieldId);
        this.alertRuleId = AlertRuleId.of(alertRuleId);
    }

    /**
     * Creates a new Alert from a {@link SendAlertCommand}.
     * Registers an {@link AlertSentEvent} to be published after save.
     */
    public Alert(SendAlertCommand command) {
        this.message = command.message();
        this.priority = command.priority();
        this.isRead = false;
        this.fieldId = FieldId.of(command.fieldId());
        this.alertRuleId = AlertRuleId.of(command.alertRuleId());
        registerDomainEvent(new AlertSentEvent(this));
    }

    /**
     * Reconstitutes an Alert from persistence, binding its typed identity.
     *
     * @param rawId the surrogate key assigned by the database
     * @return this instance (fluent)
     */
    public Alert reconstitute(Long rawId) {
        this.id = AlertId.of(rawId);
        return this;
    }

    /**
     * Marks this alert as read.
     *
     * @param command the mark alert read command
     * @throws IllegalStateException if the alert is already read
     */
    public void markAsRead(MarkAlertReadCommand command) {
        if (this.isRead)
            throw new IllegalStateException("Alert is already marked as read");
        this.isRead = true;
    }
}