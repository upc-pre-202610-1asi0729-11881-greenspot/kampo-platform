package com.acme.kampo.platform.organization.domain.model.commands;

/**
 * Command to register a new field within an existing fundo.
 *
 * @param fundoId  ID of the fundo this field belongs to
 * @param name     name of the field
 * @param areaSqm  field area in square metres — must be positive
 */
public record RegisterFieldCommand(Long fundoId, String name, Double areaSqm) {
    public RegisterFieldCommand {
        if (fundoId == null || fundoId <= 0)
            throw new IllegalArgumentException("fundoId must be positive");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name must not be blank");
        if (areaSqm == null || areaSqm <= 0)
            throw new IllegalArgumentException("areaSqm must be positive");
    }
}
