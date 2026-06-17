package com.acme.kampo.platform.financial.interfaces.rest.resources;


import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Outbound resource representing an Expense in API responses.
 */
@Schema(description = "Expense representation")
public record ExpenseResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Description of the expense", example = "Compra de fertilizante NPK")
        String description,

        @Schema(description = "Monetary amount", example = "500.00")
        BigDecimal amount,

        @Schema(description = "ISO-4217 currency code", example = "PEN")
        String currency,

        @Schema(description = "Expense type", example = "INPUT")
        ExpenseType type,

        @Schema(description = "ID of the expense category", example = "1")
        Long categoryId,

        @Schema(description = "ID of the fundo", example = "1")
        Long fundoId,

        @Schema(description = "Date the expense was incurred", example = "2025-06-01")
        LocalDate date
) {}
