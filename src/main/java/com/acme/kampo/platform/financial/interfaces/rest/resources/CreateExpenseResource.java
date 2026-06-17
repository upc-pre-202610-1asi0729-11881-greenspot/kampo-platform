package com.acme.kampo.platform.financial.interfaces.rest.resources;


import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Inbound resource for registering a new expense.
 * Received as JSON body in POST /api/v1/expenses.
 */
@Schema(description = "Resource to register a new expense")
public record CreateExpenseResource(

        @Schema(description = "Human-readable description of the expense", example = "Compra de fertilizante NPK")
        String description,

        @Schema(description = "Monetary amount — must be positive", example = "500.00")
        BigDecimal amount,

        @Schema(description = "ISO-4217 currency code", example = "PEN")
        String currency,

        @Schema(description = "Expense type classification", example = "INPUT")
        ExpenseType type,

        @Schema(description = "ID of the expense category", example = "1")
        Long categoryId,

        @Schema(description = "ID of the fundo this expense belongs to", example = "1")
        Long fundoId,

        @Schema(description = "Date the expense was incurred", example = "2025-06-01")
        LocalDate date
) {}
 
