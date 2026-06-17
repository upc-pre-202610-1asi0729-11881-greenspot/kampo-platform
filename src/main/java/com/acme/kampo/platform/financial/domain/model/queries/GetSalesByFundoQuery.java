package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;

public record GetSalesByFundoQuery(FundoId fundoId) {
    public GetSalesByFundoQuery { if (fundoId == null) throw new IllegalArgumentException("fundoId must not be null"); }
}
