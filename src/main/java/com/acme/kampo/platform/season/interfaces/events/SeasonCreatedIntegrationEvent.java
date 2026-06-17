package com.acme.kampo.platform.season.interfaces.events;

public record SeasonCreatedIntegrationEvent(
        Long seasonId,
        Long fieldId,
        String cropName,
        java.time.LocalDate startedAt) {
}