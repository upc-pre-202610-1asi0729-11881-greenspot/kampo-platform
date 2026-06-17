package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;

public record GetExpensesByTypeQuery(ExpenseType type) {
    public GetExpensesByTypeQuery { if (type == null) throw new IllegalArgumentException("type must not be null"); }
}

