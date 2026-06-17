package com.acme.kampo.platform.organization.domain.model.queries;
import com.acme.kampo.platform.organization.domain.model.valueobjects.FundoId;
public record GetFundoByIdQuery(FundoId fundoId) {
    public GetFundoByIdQuery { if (fundoId == null) throw new IllegalArgumentException("fundoId must not be null"); }
}
