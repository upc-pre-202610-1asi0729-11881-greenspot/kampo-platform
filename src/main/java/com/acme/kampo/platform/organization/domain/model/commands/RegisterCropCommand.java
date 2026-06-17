package com.acme.kampo.platform.organization.domain.model.commands;

import java.time.LocalDate;

/**
 * Command to register a new crop within an existing field.
 *
 * @param fieldId   ID of the field this crop belongs to
 * @param name      name of the crop (e.g. "Papa Amarilla", "Maíz")
 * @param plantedAt date the crop was planted
 */
public record RegisterCropCommand(Long fieldId, String name, LocalDate plantedAt) {
    public RegisterCropCommand {
        if (fieldId == null || fieldId <= 0)
            throw new IllegalArgumentException("fieldId must be positive");
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name must not be blank");
        if (plantedAt == null)
            throw new IllegalArgumentException("plantedAt must not be null");
        if (plantedAt.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("plantedAt must not be in the future");
    }
}