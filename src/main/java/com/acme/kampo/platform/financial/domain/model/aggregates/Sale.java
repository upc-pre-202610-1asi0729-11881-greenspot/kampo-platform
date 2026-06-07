package com.acme.kampo.platform.financial.domain.model.aggregates;

import com.acme.kampo.platform.financial.domain.model.command.RegisterSaleCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateSaleCommand;
import com.acme.kampo.platform.financial.domain.model.events.SaleRegisteredEvent;
import com.acme.kampo.platform.financial.domain.model.valueObjects.FundoId;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SaleId;
import com.acme.kampo.platform.shared.domain.model.aggregates.AbstractDomainAggregateRoot;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Aggregate root representing a crop sale made by a fundo.
 *
 * <p>Automatically calculates {@code totalAmount = pricePerUnit × quantity} using
 * {@link Money#multiply(BigDecimal)} on construction and update.
 * Publishes {@link SaleRegisteredEvent} on creation.</p>
 */
@Getter
public class Sale extends AbstractDomainAggregateRoot<Sale> {

    private SaleId    id;
    private String    cropName;
    private Double    quantity;
    private Money     pricePerUnit;
    private Money     totalAmount;
    private FundoId   fundoId;
    private LocalDate date;
    private boolean   cancelled;

    /** Required by JPA proxy — do not use directly. */
    protected Sale() {}

    /**
     * Reconstitution constructor — rebuilds from persisted values without
     * triggering any domain logic or events.
     */
    public Sale(Long id, String cropName, Double quantity,
                Money pricePerUnit, Money totalAmount,
                Long fundoId, LocalDate date, boolean cancelled) {
        this.id           = SaleId.of(id);
        this.cropName     = cropName;
        this.quantity     = quantity;
        this.pricePerUnit = pricePerUnit;
        this.totalAmount  = totalAmount;
        this.fundoId      = FundoId.of(fundoId);
        this.date         = date;
        this.cancelled    = cancelled;
    }

    /**
     * Creation constructor — builds from a {@link RegisterSaleCommand}.
     * Calculates totalAmount automatically and registers a {@link SaleRegisteredEvent}.
     *
     * @param command the registration command
     */
    public Sale(RegisterSaleCommand command) {
        this.cropName     = command.cropName();
        this.quantity     = command.quantity();
        this.pricePerUnit = Money.of(command.pricePerUnit(), command.currency());
        this.totalAmount  = calculateTotal(command.pricePerUnit(),
                command.quantity(), command.currency());
        this.fundoId      = FundoId.of(command.fundoId());
        this.date         = command.date();
        this.cancelled    = false;
        registerDomainEvent(new SaleRegisteredEvent(this));
    }

    // ── Behaviour ─────────────────────────────────────────────────────────────

    /**
     * Updates crop name, quantity and price per unit.
     * Recalculates {@code totalAmount} automatically.
     * Currency is preserved.
     *
     * @param command the update command
     * @throws IllegalStateException if the sale is already cancelled
     */
    public void update(UpdateSaleCommand command) {
        if (cancelled)
            throw new IllegalStateException("Cannot update a cancelled sale");
        this.cropName     = command.cropName();
        this.quantity     = command.quantity();
        this.pricePerUnit = Money.of(command.pricePerUnit(), pricePerUnit.currency());
        this.totalAmount  = calculateTotal(command.pricePerUnit(),
                command.quantity(), pricePerUnit.currency());
    }

    /**
     * Marks the sale as cancelled. Terminal — cannot be reversed.
     *
     * @throws IllegalStateException if the sale is already cancelled
     */
    public void cancel() {
        if (cancelled)
            throw new IllegalStateException("Sale is already cancelled");
        this.cancelled = true;
    }

    /**
     * Encapsulates the total amount calculation: pricePerUnit × quantity.
     */
    private static Money calculateTotal(BigDecimal pricePerUnit, Double quantity, String currency) {
        return Money.of(pricePerUnit, currency)
                .multiply(BigDecimal.valueOf(quantity));
    }

    public Sale reconstitute(Long rawId) {
        this.id = SaleId.of(rawId);
        return this;
    }
}