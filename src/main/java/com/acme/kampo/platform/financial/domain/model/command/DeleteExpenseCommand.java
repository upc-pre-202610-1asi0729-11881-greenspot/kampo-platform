package com.acme.kampo.platform.financial.domain.model.command;

/**
 * Command to delete an expense by its ID.
 *
 * @param expenseId ID of the expense to delete
 */
public record DeleteExpenseCommand(Long expenseId) {
    public DeleteExpenseCommand {
        if (expenseId == null || expenseId <= 0)
            throw new IllegalArgumentException("expenseId must be positive");
    }
}
