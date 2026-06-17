package com.acme.kampo.platform.organization.domain.model.commands;

/**
 * Command to register a new fundo under an existing organization.
 *
 * @param organizationId ID of the organization this fundo belongs to
 * @param name           name of the fundo
 * @param latitude       geographic latitude in decimal degrees (-90 to +90)
 * @param longitude      geographic longitude in decimal degrees (-180 to +180)
 */
public record RegisterFundoCommand(
        Long organizationId,
        String name,
        Double latitude,
        Double longitude
) {
    public RegisterFundoCommand {
        if (organizationId == null || organizationId <= 0)
            throw new IllegalArgumentException("organizationId must be positive");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name must not be blank");
        if (latitude == null || latitude < -90 || latitude > 90)
            throw new IllegalArgumentException("latitude must be between -90 and 90");
        if (longitude == null || longitude < -180 || longitude > 180)
            throw new IllegalArgumentException("longitude must be between -180 and 180");
    }
}