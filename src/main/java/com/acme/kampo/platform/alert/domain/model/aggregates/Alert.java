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
 * Aggregate root representing a triggered alert for a field.
 *
 * <p>An alert is created when a sensor reading violates an {@link AlertRule}.
 * It starts unread and can be marked as read via {@link #markAsRead(MarkAlertReadCommand)}.
 *
 * <p>Publishes {@link AlertSentEvent} on creation so other bounded contexts
 * can react (e.g. send a push notification or log the incident).</p>
 */

public class Alert extends AbstractDomainAggregateRoot<Alert> {

    @Getter
    private AlertId       id;
    @Getter
    private String        message;
    @Getter
    private AlertPriority priority;
    private boolean       isRead;
    @Getter
    private FieldId       fieldId;
    @Getter
    private AlertRuleId   alertRuleId;

    /** Required by JPA proxy — do not use directly. */
    protected Alert() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     * Used exclusively by the persistence assembler.
     */
    public Alert(Long id, String message, AlertPriority priority,
                 boolean isRead, Long fieldId, Long alertRuleId) {
        this.id          = AlertId.of(id);
        this.message     = message;
        this.priority    = priority;
        this.isRead      = isRead;
        this.fieldId     = FieldId.of(fieldId);
        this.alertRuleId = AlertRuleId.of(alertRuleId);
    }

    /**
     * Creation constructor — builds from a {@link SendAlertCommand}.
     * Alert starts as unread and registers an {@link AlertSentEvent}.
     *
     * @param command the send command carrying message, priority, field and rule
     */
    public Alert(SendAlertCommand command) {
        this.message     = command.message();
        this.priority    = command.priority();
        this.isRead      = false;
        this.fieldId     = FieldId.of(command.fieldId());
        this.alertRuleId = AlertRuleId.of(command.alertRuleId());
        registerDomainEvent(new AlertSentEvent(this));
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Marks this alert as read.
     *
     * @param command the mark-read command (carries the alertId for validation)
     * @throws IllegalStateException if the alert is already read
     */
    public void markAsRead(MarkAlertReadCommand command) {
        if (isRead)
            throw new IllegalStateException(
                    "Alert %d is already marked as read".formatted(
                            id != null ? id.getValue() : 0));
        this.isRead = true;
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public boolean       getIsRead()      { return isRead; }

    public Alert reconstitute(Long rawId) {
        this.id = AlertId.of(rawId);
        return this;
    }
}