package com.acme.kampo.platform.organization.domain.model.aggregates;

import com.acme.kampo.platform.organization.domain.model.commands.RegisterFieldCommand;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FundoId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

/**
 * Aggregate root representing a field (parcel) within a fundo.
 *
 * <p>A field has a measured area and belongs to exactly one {@link Fundo}
 * referenced by {@link FundoId}. Crops are separate aggregates referenced by
 * {@link com.acme.kampo.platform.organization.domain.model.valueobjects.CropId}.</p>
 */
@Getter
public class Field extends AbstractDomainAggregateRoot<Field> {

    private FieldId id;
    private FundoId fundoId;
    private String  name;
    private Double  areaSqm;

    /** Required by JPA proxy — do not use directly. */
    protected Field() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public Field(Long id, Long fundoId, String name, Double areaSqm) {
        this.id      = FieldId.of(id);
        this.fundoId = FundoId.of(fundoId);
        this.name    = name;
        this.areaSqm = areaSqm;
    }

    /**
     * Creation constructor — builds from a {@link RegisterFieldCommand}.
     *
     * @param command the registration command
     */
    public Field(RegisterFieldCommand command) {
        this.fundoId = FundoId.of(command.fundoId());
        this.name    = command.name();
        this.areaSqm = command.areaSqm();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public Field reconstitute(Long rawId) {
        this.id = FieldId.of(rawId);
        return this;
    }
}
