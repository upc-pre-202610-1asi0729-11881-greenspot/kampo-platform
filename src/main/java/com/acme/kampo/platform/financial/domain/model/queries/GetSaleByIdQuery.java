package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.valueObjects.SaleId;

public record GetSaleByIdQuery(SaleId saleId) {
    public GetSaleByIdQuery { if (saleId == null) throw new IllegalArgumentException("saleId must not be null"); }
}
