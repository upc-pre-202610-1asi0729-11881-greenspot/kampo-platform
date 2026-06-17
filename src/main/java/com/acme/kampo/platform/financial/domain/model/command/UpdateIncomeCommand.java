package com.acme.kampo.platform.financial.domain.model.command;


import java.math.BigDecimal;

/**
 * Command to update the mutable fields of an existing income entry.
 *
 * @param incomeId    ID of the income to update
 * @param description updated description
 * @param amount      updated monetary amount — must be positive
 */
public record UpdateIncomeCommand(Long incomeId, String description, BigDecimal amount) {
    public UpdateIncomeCommand {
        if (incomeId == null || incomeId <= 0)
            throw new IllegalArgumentException("incomeId must be positive");
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description must not be blank");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount must be positive");
    }
}
