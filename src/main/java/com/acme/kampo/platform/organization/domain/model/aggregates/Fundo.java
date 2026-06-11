package com.acme.kampo.platform.organization.domain.model.aggregates;

import com.acme.kampo.platform.organization.domain.model.commands.RegisterFundoCommand;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FundoId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.GeoLocation;
import com.acme.kampo.platform.organization.domain.model.valueobjects.OrganizationId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

/**
 * Aggregate root representing a fundo (agricultural estate) owned by an organization.
 *
 * <p>A fundo has a geographic location and belongs to exactly one {@link Organization}
 * referenced by {@link OrganizationId}. Fields are separate aggregates referenced by
 * {@link com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId}.</p>
 *
 * <p>No domain event is published on creation — fundo registration is an
 * operational action, not a business-significant event for other contexts.</p>
 */
public class Fundo extends AbstractDomainAggregateRoot<Fundo> {

    private FundoId        id;
    private OrganizationId organizationId;
    private String         name;
    private GeoLocation    location;

    /** Required by JPA proxy — do not use directly. */
    protected Fundo() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public Fundo(Long id, Long organizationId, String name, Double latitude, Double longitude) {
        this.id             = FundoId.of(id);
        this.organizationId = OrganizationId.of(organizationId);
        this.name           = name;
        this.location       = GeoLocation.of(latitude, longitude);
    }

    /**
     * Creation constructor — builds from a {@link RegisterFundoCommand}.
     *
     * @param command the registration command
     */
    public Fundo(RegisterFundoCommand command) {
        this.organizationId = OrganizationId.of(command.organizationId());
        this.name           = command.name();
        this.location       = GeoLocation.of(command.latitude(), command.longitude());
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public FundoId        getId()             { return id; }
    public OrganizationId getOrganizationId() { return organizationId; }
    public String         getName()           { return name; }
    public GeoLocation    getLocation()       { return location; }

    public Fundo reconstitute(Long rawId) {
        this.id = FundoId.of(rawId);
        return this;
    }
}