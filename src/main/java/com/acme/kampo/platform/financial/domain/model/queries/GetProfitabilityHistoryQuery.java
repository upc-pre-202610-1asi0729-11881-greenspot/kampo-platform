package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;

public record GetProfitabilityHistoryQuery(FundoId fundoId) {
    public GetProfitabilityHistoryQuery { if (fundoId == null) throw new IllegalArgumentException("fundoId must not be null"); }
}