package com.acme.kampo.platform.financial.domain.model.aggregates;


import com.acme.kampo.platform.financial.domain.model.command.RegisterIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateIncomeCommand;
import com.acme.kampo.platform.financial.domain.model.events.IncomeRegisteredEvent;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.IncomeId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.time.LocalDate;

/**
 * Aggregate root representing a financial income entry for a fundo.
 *
 * <p>Publishes {@link IncomeRegisteredEvent} on creation.</p>
 */

@Getter
public class Income extends AbstractDomainAggregateRoot<Income> {

    private IncomeId id;
    private String    description;
    private Money amount;
    private FundoId fundoId;
    private LocalDate date;

    /** Required by JPA proxy — do not use directly. */
    protected Income() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public Income(Long id, String description, Money amount, Long fundoId, LocalDate date) {
        this.id          = IncomeId.of(id);
        this.description = description;
        this.amount      = amount;
        this.fundoId     = FundoId.of(fundoId);
        this.date        = date;
    }

    /**
     * Creation constructor — builds from a {@link RegisterIncomeCommand} and
     * registers an {@link IncomeRegisteredEvent} to be published after save.
     */
    public Income(RegisterIncomeCommand command) {
        this.description = command.description();
        this.amount      = Money.of(command.amount(), command.currency());
        this.fundoId     = FundoId.of(command.fundoId());
        this.date        = command.date();
        registerDomainEvent(new IncomeRegisteredEvent(this));
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Updates description and amount. Currency is preserved.
     *
     * @param command the update command
     */
    public void update(UpdateIncomeCommand command) {
        this.description = command.description();
        this.amount      = Money.of(command.amount(), this.amount.currency());
    }

    public Income reconstitute(Long rawId) {
        this.id = IncomeId.of(rawId);
        return this;
    }
}
