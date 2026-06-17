package com.acme.kampo.platform.organization.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value object representing the unique identity of a Organization aggregate.
 * Immutable — use {@code OrganizationId.of(...)} to construct instances.
 */
@Embeddable
public record OrganizationId(Long value) {

    public OrganizationId {
        if (value != null && value <= 0)
            throw new IllegalArgumentException("OrganizationId value must be positive, got: " + value);
    }

    public static OrganizationId of(Long value) { return new OrganizationId(value); }
    public static OrganizationId of(long value) { return new OrganizationId(value); }
    public Long getValue() { return value; }

    @Override
    public String toString() { return "OrganizationId(" + value + ")"; }
}
