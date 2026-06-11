package com.acme.kampo.platform.organization.infrastructure.persistence.jpa.embeddables;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

/**
 * Persistence representation of the {@link com.acme.kampo.platform.organization.domain.model.valueobjects.GeoLocation}
 * value object.
 *
 * <p>Maps to two columns: {@code latitude} and {@code longitude}.
 * Translation handled by {@link com.acme.kampo.platform.organization.infrastructure.persistence.jpa.assemblers.FundoPersistenceAssembler}.</p>
 */
@Setter
@Getter
@Embeddable
public class GeoLocationPersistenceEmbeddable {

    @Column(nullable = false)
    private Double latitude;

    @Column(nullable = false)
    private Double longitude;

    public GeoLocationPersistenceEmbeddable() {}

    public GeoLocationPersistenceEmbeddable(Double latitude, Double longitude) {
        this.latitude  = latitude;
        this.longitude = longitude;
    }

}