package com.acme.kampo.platform.financial.application.internal.queryservices;


import com.acme.kampo.platform.financial.application.queryservices.ExpenseQueryService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;
import com.acme.kampo.platform.financial.domain.model.queries.*;
import com.acme.kampo.platform.financial.domain.repository.ExpenseCategoryRepository;
import com.acme.kampo.platform.financial.domain.repository.ExpenseRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link ExpenseQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class ExpenseQueryServiceImpl implements ExpenseQueryService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;

    public ExpenseQueryServiceImpl(ExpenseRepository expenseRepository,
                                   ExpenseCategoryRepository categoryRepository) {
        this.expenseRepository  = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Optional<Expense> handle(GetExpenseByIdQuery query) {
        return expenseRepository.findById(query.expenseId());
    }

    @Override
    public List<Expense> handle(GetExpensesByFundoQuery query) {
        return expenseRepository.findByFundoId(query.fundoId());
    }

    @Override
    public List<Expense> handle(GetExpensesByTypeQuery query) {
        return expenseRepository.findByType(query.type());
    }

    @Override
    public List<Expense> handle(GetAllExpensesQuery query) {
        return expenseRepository.findAll();
    }

    @Override
    public List<ExpenseCategory> handle(GetExpenseCategoriesQuery query) {
        return categoryRepository.findAll();
    }
}

