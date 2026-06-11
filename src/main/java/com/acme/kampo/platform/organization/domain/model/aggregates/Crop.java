package com.acme.kampo.platform.organization.domain.model.aggregates;

import com.acme.kampo.platform.organization.domain.model.commands.RegisterCropCommand;
import com.acme.kampo.platform.organization.domain.model.valueobjects.CropId;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FieldId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;

import java.time.LocalDate;

/**
 * Aggregate root representing a crop planted in a field.
 *
 * <p>A crop records what was planted, when, and in which field (by {@link FieldId}).
 * It is the leaf aggregate in the organization hierarchy:
 * Organization → Fundo → Field → Crop.</p>
 */
public class Crop extends AbstractDomainAggregateRoot<Crop> {

    private CropId    id;
    private FieldId   fieldId;
    private String    name;
    private LocalDate plantedAt;

    /** Required by JPA proxy — do not use directly. */
    protected Crop() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public Crop(Long id, Long fieldId, String name, LocalDate plantedAt) {
        this.id        = CropId.of(id);
        this.fieldId   = FieldId.of(fieldId);
        this.name      = name;
        this.plantedAt = plantedAt;
    }

    /**
     * Creation constructor — builds from a {@link RegisterCropCommand}.
     *
     * @param command the registration command
     */
    public Crop(RegisterCropCommand command) {
        this.fieldId   = FieldId.of(command.fieldId());
        this.name      = command.name();
        this.plantedAt = command.plantedAt();
    }

    // ── Getters ───────────────────────────────────────────────────────────────

    public CropId    getId()        { return id; }
    public FieldId   getFieldId()   { return fieldId; }
    public String    getName()      { return name; }
    public LocalDate getPlantedAt() { return plantedAt; }

    public Crop reconstitute(Long rawId) {
        this.id = CropId.of(rawId);
        return this;
    }
}