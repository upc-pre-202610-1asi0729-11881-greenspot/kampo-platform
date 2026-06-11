package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers;

import com.acme.kampo.platform.organization.domain.model.aggregates.Fundo;
import com.acme.kampo.platform.organization.domain.model.valueobjects.GeoLocation;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.embeddables.GeoLocationPersistenceEmbeddable;
import com.acme.kampo.platform.organization.infrastructure.persistence.jpa.entities.FundoPersistenceEntity;

/**
 * Static assembler between the {@link Fundo} aggregate and its JPA persistence representation.
 * Handles the translation of {@link GeoLocation} to {@link GeoLocationPersistenceEmbeddable}.
 */
public final class FundoPersistenceAssembler {

    private FundoPersistenceAssembler() {}

    public static Fundo toDomainFromPersistence(FundoPersistenceEntity entity) {
        return new Fundo(
                entity.getId(),
                entity.getOrganizationId(),
                entity.getName(),
                entity.getLocation().getLatitude(),
                entity.getLocation().getLongitude());
    }

    public static FundoPersistenceEntity toPersistenceFromDomain(Fundo fundo) {
        var entity = new FundoPersistenceEntity();
        entity.setId(fundo.getId() != null ? fundo.getId().getValue() : null);
        entity.setOrganizationId(fundo.getOrganizationId().getValue());
        entity.setName(fundo.getName());
        entity.setLocation(new GeoLocationPersistenceEmbeddable(
                fundo.getLocation().latitude(),
                fundo.getLocation().longitude()));
        return entity;
    }
}