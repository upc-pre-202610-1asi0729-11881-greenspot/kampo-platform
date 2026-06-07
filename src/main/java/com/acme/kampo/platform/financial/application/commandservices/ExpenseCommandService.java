package com.acme.kampo.platform.financial.application.commandservices;


import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.command.CreateExpenseCategoryCommand;
import com.acme.kampo.platform.financial.domain.model.command.DeleteExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.command.RegisterExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;

/**
 * Application service contract for {@link Expense} and {@link ExpenseCategory} write operations.
 */
public interface ExpenseCommandService {

    /**
     * Registers a new expense.
     *
     * @param command the registration command
     * @return {@link Result} with the created {@link Expense} or an {@link ApplicationError}
     */
    Result<Expense, ApplicationError> handle(RegisterExpenseCommand command);

    /**
     * Updates an existing expense.
     *
     * @param command the update command
     * @return {@link Result} with the updated {@link Expense} or an {@link ApplicationError}
     */
    Result<Expense, ApplicationError> handle(UpdateExpenseCommand command);

    /**
     * Deletes an existing expense.
     *
     * @param command the delete command
     * @return {@link Result} with {@code null} on success or an {@link ApplicationError}
     */
    Result<Void, ApplicationError> handle(DeleteExpenseCommand command);

    /**
     * Creates a new expense category.
     *
     * @param command the creation command
     * @return {@link Result} with the created {@link ExpenseCategory} or an {@link ApplicationError}
     */
    Result<ExpenseCategory, ApplicationError> handle(CreateExpenseCategoryCommand command);
}