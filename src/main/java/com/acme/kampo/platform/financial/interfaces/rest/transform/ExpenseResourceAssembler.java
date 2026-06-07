package com.acme.kampo.platform.financial.interfaces.rest.transform;


import com.acme.kampo.platform.financial.domain.model.aggregates.Expense;
import com.acme.kampo.platform.financial.domain.model.command.RegisterExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.entities.ExpenseCategory;
import com.acme.kampo.platform.financial.domain.model.command.CreateExpenseCategoryCommand;
import com.acme.kampo.platform.financial.interfaces.rest.resources.*;

/**
 * Assembler for Expense and ExpenseCategory resource ↔ command/domain translations.
 */
public final class ExpenseResourceAssembler {

    private ExpenseResourceAssembler() {}

    public static RegisterExpenseCommand toCommand(CreateExpenseResource resource) {
        return new RegisterExpenseCommand(
                resource.description(), resource.amount(), resource.currency(),
                resource.type(), resource.categoryId(), resource.fundoId(), resource.date());
    }

    public static UpdateExpenseCommand toCommand(Long expenseId, UpdateExpenseResource resource) {
        return new UpdateExpenseCommand(
                expenseId, resource.description(), resource.amount(), resource.type());
    }

    public static CreateExpenseCategoryCommand toCommand(CreateExpenseCategoryResource resource) {
        return new CreateExpenseCategoryCommand(resource.name(), resource.description());
    }

    public static ExpenseResource toResource(Expense expense) {
        return new ExpenseResource(
                expense.getId().getValue(),
                expense.getDescription(),
                expense.getAmount().amount(),
                expense.getAmount().currency(),
                expense.getType(),
                expense.getCategoryId(),
                expense.getFundoId().getValue(),
                expense.getDate());
    }

    public static ExpenseCategoryResource toResource(ExpenseCategory category) {
        return new ExpenseCategoryResource(
                category.getId(), category.getName(), category.getDescription());
    }
}
