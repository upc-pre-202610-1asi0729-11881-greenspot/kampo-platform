package com.acme.kampo.platform.financial.interfaces.rest.resources;


import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Inbound resource for creating an expense category.
 */
@Schema(description = "Resource to create a new expense category")
public record CreateExpenseCategoryResource(

        @Schema(description = "Category name — must be unique", example = "Insumos agrícolas")
        String name,

        @Schema(description = "Optional description of the category", example = "Semillas, fertilizantes y pesticidas")
        String description
) {}
 
