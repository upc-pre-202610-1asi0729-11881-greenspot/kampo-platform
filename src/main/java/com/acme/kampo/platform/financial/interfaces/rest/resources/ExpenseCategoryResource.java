package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Outbound resource representing an ExpenseCategory in API responses.
 */
@Schema(description = "Expense category representation")
public record ExpenseCategoryResource(

        @Schema(description = "Unique identifier", example = "1")
        Long id,

        @Schema(description = "Category name", example = "Insumos agrícolas")
        String name,

        @Schema(description = "Category description", example = "Semillas, fertilizantes y pesticidas")
        String description
) {}
 
