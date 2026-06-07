package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;

public record GetExpensesByFundoQuery(FundoId fundoId) {
    public GetExpensesByFundoQuery { if (fundoId == null) throw new IllegalArgumentException("fundoId must not be null"); }
}