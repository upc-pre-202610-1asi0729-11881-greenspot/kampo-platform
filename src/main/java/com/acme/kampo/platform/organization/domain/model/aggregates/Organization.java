package com.acme.kampo.platform.organization.domain.model.aggregates;

import com.acme.kampo.platform.organization.domain.model.commands.CreateOrganizationCommand;
import com.acme.kampo.platform.organization.domain.model.commands.UpdateOrganizationCommand;
import com.acme.kampo.platform.organization.domain.model.events.OrganizationCreatedEvent;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

/**
 * Aggregate root representing an agricultural organization.
 *
 * <p>An organization is the top-level entity that owns one or more fundos.
 * Fundos, fields and crops are separate aggregates referenced by ID.</p>
 *
 * <p>Publishes {@link OrganizationCreatedEvent} on creation.</p>
 */
public class Organization extends AbstractDomainAggregateRoot<Organization> {

    private OrganizationId id;
    private String         name;
    private String         address;

    /** Required by JPA proxy — do not use directly. */
    protected Organization() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     * Used exclusively by the persistence assembler.
     */
    public Organization(Long id, String name, String address) {
        this.id      = OrganizationId.of(id);
        this.name    = name;
        this.address = address;
    }

    /**
     * Creation constructor — builds from a {@link CreateOrganizationCommand}
     * and registers an {@link OrganizationCreatedEvent}.
     *
     * @param command the creation command
     */
    public Organization(CreateOrganizationCommand command) {
        this.name    = command.name();
        this.address = command.address();
        registerDomainEvent(new OrganizationCreatedEvent(this));
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Updates the organization's name and address.
     *
     * @param command the update command
     */
    public void update(UpdateOrganizationCommand command) {
        this.name    = command.name();
        this.address = command.address();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public OrganizationId getId()      { return id; }
    public String         getName()    { return name; }
    public String         getAddress() { return address; }

    public Organization reconstitute(Long rawId) {
        this.id = OrganizationId.of(rawId);
        return this;
    }
}