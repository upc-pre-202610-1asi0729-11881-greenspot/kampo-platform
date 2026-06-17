package com.acme.kampo.platform.financial.domain.model.command;


import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Command to register a new income entry for a fundo.
 *
 * @param description human-readable description of the income source
 * @param amount      monetary amount — must be positive
 * @param currency    ISO-4217 currency code
 * @param fundoId     ID of the fundo this income belongs to
 * @param date        date the income was received
 */
public record RegisterIncomeCommand(
        String description,
        BigDecimal amount,
        String currency,
        Long fundoId,
        LocalDate date
) {
    public RegisterIncomeCommand {
        if (description == null || description.isBlank())
            throw new IllegalArgumentException("description must not be blank");
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("amount must be positive");
        if (currency == null || currency.length() != 3)
            throw new IllegalArgumentException("currency must be a 3-letter ISO-4217 code");
        if (fundoId == null || fundoId <= 0)
            throw new IllegalArgumentException("fundoId must be positive");
        if (date == null)
            throw new IllegalArgumentException("date must not be null");
    }
}
