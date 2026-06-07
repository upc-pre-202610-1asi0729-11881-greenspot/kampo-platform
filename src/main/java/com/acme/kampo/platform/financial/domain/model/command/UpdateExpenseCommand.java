package com.acme.kampo.platform.financial.domain.model.command;


import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;

import java.math.BigDecimal;

/**
 * Command to update the mutable fields of an existing expense.
 *
 * @param expenseId   ID of the expense to update
 * @param description updated description
 * @param amount      updated monetary amount — must be positive
 * @param type        updated expense type
 */
public record UpdateExpenseCommand(
        Long expenseId,
        String description,
        BigDecimal amount,
        ExpenseType type
) {
    public UpdateExpenseCommand {
        if (expenseId == null || expenseId <= 0)
            throw new IllegalArgumentException("expenseId must be positive");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description must not be blank");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount must be positive");
        if (type == null)
            throw new IllegalArgumentException("type must not be null");
    }
}
