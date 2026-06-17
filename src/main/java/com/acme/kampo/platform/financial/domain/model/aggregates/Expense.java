package com.acme.kampo.platform.financial.domain.model.aggregates;

import com.acme.kampo.platform.financial.domain.model.command.RegisterExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateExpenseCommand;
import com.acme.kampo.platform.financial.domain.model.enums.ExpenseType;
import com.acme.kampo.platform.financial.domain.model.events.ExpenseRegisteredEvent;
import com.acme.kampo.platform.financial.domain.model.valueObjects.ExpenseId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;


/**
 * Aggregate root representing a financial expense within a fundo.
 *
 * <p>Responsible for:
 * <ul>
 *   <li>Holding a monetary amount, type, category reference and date.</li>
 *   <li>Allowing in-place updates of description, amount and type.</li>
 *   <li>Publishing {@link ExpenseRegisteredEvent} on creation.</li>
 * </ul>
 */
@Getter
public class Expense extends AbstractDomainAggregateRoot<Expense> {

    private ExpenseId id;
    private String     description;
    private Money amount;
    private ExpenseType type;
    private Long       categoryId;
    private FundoId fundoId;
    private LocalDate date;

    /** Required by JPA proxy — do not use directly. */
    protected Expense() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     * Used exclusively by {@link com.acme.kampo.platform.financial.infrastructure.persistence.jpa.assemblers.ExpensePersistenceAssembler}.
     */
    public Expense(Long id, String description, Money amount, ExpenseType type,
                   Long categoryId, Long fundoId, LocalDate date) {
        this.id          = ExpenseId.of(id);
        this.description = description;
        this.amount      = amount;
        this.type        = type;
        this.categoryId  = categoryId;
        this.fundoId     = FundoId.of(fundoId);
        this.date        = date;
    }

    /**
     * Creation constructor — builds from a {@link RegisterExpenseCommand} and
     * registers an {@link ExpenseRegisteredEvent} to be published after save.
     *
     * @param command the registration command
     */
    public Expense(RegisterExpenseCommand command) {
        this.description = command.description();
        this.amount      = Money.of(command.amount(), command.currency());
        this.type        = command.type();
        this.categoryId  = command.categoryId();
        this.fundoId     = FundoId.of(command.fundoId());
        this.date        = command.date();
        registerDomainEvent(new ExpenseRegisteredEvent(this));
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Updates description, amount and type from an {@link UpdateExpenseCommand}.
     * Currency is preserved — the update cannot change the expense currency.
     *
     * @param command the update command
     */
    public void update(UpdateExpenseCommand command) {
        this.description = command.description();
        this.amount      = Money.of(command.amount(), this.amount.currency());
        this.type        = command.type();
    }

    /**
     * Returns {@code true} if this expense belongs to the given fundo.
     *
     * @param fundoId the fundo identity to check
     */
    public boolean belongsToFundo(FundoId fundoId) {
        return this.fundoId.equals(fundoId);
    }

    /** Convenience method for the persistence assembler. */
    public Expense reconstitute(Long rawId) {
        this.id = ExpenseId.of(rawId);
        return this;
    }
}
