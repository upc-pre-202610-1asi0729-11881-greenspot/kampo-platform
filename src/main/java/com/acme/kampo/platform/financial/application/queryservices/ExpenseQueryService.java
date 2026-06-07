package com.acme.kampo.platform.financial.application.queryservices;


import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;
import com.acme.kampo.platform.financial.domain.model.queries.*;

import java.util.List;
import java.util.Optional;

/**
 * Application service contract for {@link Expense} and {@link ExpenseCategory} read operations.
 */
public interface ExpenseQueryService {

    Optional<Expense> handle(GetExpenseByIdQuery query);

    List<Expense> handle(GetExpensesByFundoQuery query);

    List<Expense> handle(GetExpensesByTypeQuery query);

    List<Expense> handle(GetAllExpensesQuery query);

    List<ExpenseCategory> handle(GetExpenseCategoriesQuery query);
}
