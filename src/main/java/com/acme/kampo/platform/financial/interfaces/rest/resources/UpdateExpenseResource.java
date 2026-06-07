package com.acme.kampo.platform.financial.interfaces.rest.resources;

import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import io.swagger.v3.oas.annotations.media.Schema;

import java.math.BigDecimal;

/**
 * Inbound resource for updating an existing expense.
 * Received as JSON body in PUT /api/v1/expenses/{id}.
 */
@Schema(description = "Resource to update an existing expense")
public record UpdateExpenseResource(

        @Schema(description = "Updated description", example = "Compra de pesticida")
        String description,

        @Schema(description = "Updated monetary amount — must be positive", example = "350.00")
        BigDecimal amount,

        @Schema(description = "Updated expense type", example = "INPUT")
        ExpenseType type
) {}