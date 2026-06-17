package com.acme.kampo.platform.financial.application.internal.commandservices;


import com.acme.kampo.platform.financial.application.commandservices.ExpenseCommandService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.command.CreateExpenseCategoryCommand;
import com.acme.kampo.platform.financial.domain.model.command.DeleteExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.command.RegisterExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ExpenseId;
import com.acme.kampo.platform.financial.domain.repository.ExpenseCategoryRepository;
import com.acme.kampo.platform.financial.domain.repository.ExpenseRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link ExpenseCommandService}.
 */
@Service
@Transactional
public class ExpenseCommandServiceImpl implements ExpenseCommandService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseCategoryRepository categoryRepository;

    public ExpenseCommandServiceImpl(ExpenseRepository expenseRepository,
                                     ExpenseCategoryRepository categoryRepository) {
        this.expenseRepository = expenseRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Result<Expense, ApplicationError> handle(RegisterExpenseCommand command) {
        if (!categoryRepository.existsById(command.categoryId()))
            return Result.failure(ApplicationError.notFound("EXPENSE_CATEGORY",
                    String.valueOf(command.categoryId())));
        try {
            var expense = expenseRepository.save(new Expense(command));
            return Result.success(expense);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "ExpenseCommandService.handle(RegisterExpenseCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Expense, ApplicationError> handle(UpdateExpenseCommand command) {
        var expenseOpt = expenseRepository.findById(ExpenseId.of(command.expenseId()));
        if (expenseOpt.isEmpty())
            return Result.failure(ApplicationError.notFound("EXPENSE",
                    String.valueOf(command.expenseId())));
        try {
            var expense = expenseOpt.get();
            expense.update(command);
            return Result.success(expenseRepository.save(expense));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "ExpenseCommandService.handle(UpdateExpenseCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(DeleteExpenseCommand command) {
        var id = ExpenseId.of(command.expenseId());
        if (!expenseRepository.existsById(id))
            return Result.failure(ApplicationError.notFound("EXPENSE",
                    String.valueOf(command.expenseId())));
        try {
            expenseRepository.delete(id);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "ExpenseCommandService.handle(DeleteExpenseCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<ExpenseCategory, ApplicationError> handle(CreateExpenseCategoryCommand command) {
        if (categoryRepository.existsByName(command.name()))
            return Result.failure(ApplicationError.conflict("EXPENSE_CATEGORY",
                    "A category named '%s' already exists".formatted(command.name())));
        try {
            var category = categoryRepository.save(new ExpenseCategory(command));
            return Result.success(category);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "ExpenseCommandService.handle(CreateExpenseCategoryCommand)", e.getMessage()));
        }
    }
}
