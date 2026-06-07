package com.acme.kampo.platform.financial.interfaces.events;


import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;

/**
 * Integration event published by the {@code financial} bounded context when a new
 * {@link Expense} is successfully registered.
 *
 * @param expenseId  the ID of the newly created expense
 * @param fundoId    the ID of the fundo the expense belongs to
 * @param amount     the monetary amount
 * @param currency   the ISO-4217 currency code
 * @param type       the expense type as a string
 */
public record ExpenseRegisteredIntegrationEvent(
        Long expenseId,
        Long fundoId,
        java.math.BigDecimal amount,
        String currency,
        String type) {

    public static ExpenseRegisteredIntegrationEvent from(Expense expense) {
        return new ExpenseRegisteredIntegrationEvent(
                expense.getId().getValue(),
                expense.getFundoId().getValue(),
                expense.getAmount().amount(),
                expense.getAmount().currency(),
                expense.getType().name());
    }
}
