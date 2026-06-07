package com.acme.kampo.platform.financial.domain.model.command;


import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Command to register a new crop sale for a fundo.
 *
 * @param cropName     name of the crop being sold
 * @param quantity     number of units sold — must be positive
 * @param pricePerUnit price per unit — must be positive
 * @param currency     ISO-4217 currency code
 * @param fundoId      ID of the fundo this sale belongs to
 * @param date         date the sale was made
 */
public record RegisterSaleCommand(
        String cropName,
        Double quantity,
        BigDecimal pricePerUnit,
        String currency,
        Long fundoId,
        LocalDate date
) {
    public RegisterSaleCommand {
        if (cropName == null || cropName.isBlank())
            throw new IllegalArgumentException("cropName must not be blank");
        if (quantity == null || quantity <= 0)
            throw new IllegalArgumentException("quantity must be positive");
        if (pricePerUnit == null || pricePerUnit.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("pricePerUnit must be positive");
        if (currency == null || currency.length() != 3)
            throw new IllegalArgumentException("currency must be a 3-letter ISO-4217 code");
        if (fundoId == null || fundoId <= 0)
            throw new IllegalArgumentException("fundoId must be positive");
        if (date == null)
            throw new IllegalArgumentException("date must not be null");
    }
}
