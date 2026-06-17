package com.acme.kampo.platform.financial.domain.model.queries;

import com.acme.kampo.platform.financial.domain.model.valueObjects.ExpenseId;

public record GetExpenseByIdQuery(ExpenseId expenseId) {
    public GetExpenseByIdQuery { if (expenseId == null) throw new IllegalArgumentException("expenseId must not be null"); }
}
