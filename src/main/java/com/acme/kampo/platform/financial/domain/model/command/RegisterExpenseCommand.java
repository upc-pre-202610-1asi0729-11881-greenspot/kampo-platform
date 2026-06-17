package com.acme.kampo.platform.financial.domain.model.command;


import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Command to register a new expense in a fundo.
 *
 * @param description human-readable description of the expense
 * @param amount      monetary amount — must be positive
 * @param currency    ISO-4217 currency code (e.g. "PEN", "USD")
 * @param type        classification of the expense
 * @param categoryId  ID of the expense category
 * @param fundoId     ID of the fundo this expense belongs to
 * @param date        date the expense was incurred
 */
public record RegisterExpenseCommand(
        String description,
        BigDecimal amount,
        String currency,
        ExpenseType type,
        Long categoryId,
        Long fundoId,
        LocalDate date
) {
    public RegisterExpenseCommand {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description must not be blank");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount must be positive");
        if (currency == null || currency.length() != 3)
            throw new IllegalArgumentException("currency must be a 3-letter ISO-4217 code");
        if (type == null)
            throw new IllegalArgumentException("type must not be null");
        if (categoryId == null || categoryId <= 0)
            throw new IllegalArgumentException("categoryId must be positive");
        if (fundoId == null || fundoId <= 0)
            throw new IllegalArgumentException("fundoId must be positive");
        if (date == null)
            throw new IllegalArgumentException("date must not be null");
    }
}