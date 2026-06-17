package com.acme.kampo.platform.financial.domain.model.queries;

public record GetSalesByCropQuery(String cropName) {
    public GetSalesByCropQuery { if (cropName == null || cropName.isBlank()) throw new IllegalArgumentException("cropName must not be blank"); }
}
