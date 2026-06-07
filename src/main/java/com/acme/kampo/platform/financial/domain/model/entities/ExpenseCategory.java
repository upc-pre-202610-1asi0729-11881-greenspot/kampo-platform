package com.acme.kampo.platform.financial.domain.model.entities;


import com.acme.kampo.platform.financial.domain.model.command.CreateExpenseCategoryCommand;
import lombok.Getter;

/**
 * Entity representing a category used to classify expenses.
 *
 * <p>Not an aggregate root — {@code ExpenseCategory} has no domain events and
 * is referenced from {@code Expense} via {@code categoryId} (Long), never
 * by direct object reference. Its lifecycle is managed independently through
 * {@link com.acme.kampo.platform.financial.domain.repository.ExpenseCategoryRepository}.</p>
 */
@Getter
public class ExpenseCategory {

    private Long id;
    private String name;
    private String description;

    /** Required by JPA proxy — do not use directly. */
    protected ExpenseCategory() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic.
     * Used exclusively by the persistence assembler.
     */
    public ExpenseCategory(Long id, String name, String description) {
        this.id          = id;
        this.name        = name;
        this.description = description;
    }

    /**
     * Creation constructor — builds a new category from a command.
     * ID is null until persisted.
     *
     * @param command the creation command
     */
    public ExpenseCategory(CreateExpenseCategoryCommand command) {
        this.name        = command.name();
        this.description = command.description();
    }

}