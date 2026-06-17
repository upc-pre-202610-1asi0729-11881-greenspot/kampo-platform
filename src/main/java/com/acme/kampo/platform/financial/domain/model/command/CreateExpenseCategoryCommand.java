package com.acme.kampo.platform.financial.domain.model.command;

/**
 * Command to create a new expense category.
 *
 * @param name        category name — must be unique within the bounded context
 * @param description optional description of the category's purpose
 */
public record CreateExpenseCategoryCommand(String name, String description) {
    public CreateExpenseCategoryCommand {
        if (name == null || name.isBlank())
            throw new IllegalArgumentException("name must not be blank");
    }
}
 