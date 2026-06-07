package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.valueObjects.IncomeId;

public record GetIncomeByIdQuery(IncomeId incomeId) {
    public GetIncomeByIdQuery { if (incomeId == null) throw new IllegalArgumentException("incomeId must not be null"); }
}
