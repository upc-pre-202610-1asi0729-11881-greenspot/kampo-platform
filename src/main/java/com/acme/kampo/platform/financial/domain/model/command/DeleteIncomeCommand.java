package com.acme.kampo.platform.financial.domain.model.command;

/**
 * Command to delete an income entry by its ID.
 *
 * @param incomeId ID of the income to delete
 */
public record DeleteIncomeCommand(Long incomeId) {
    public DeleteIncomeCommand {
        if (incomeId == null || incomeId <= 0)
            throw new IllegalArgumentException("incomeId must be positive");
    }
}
 