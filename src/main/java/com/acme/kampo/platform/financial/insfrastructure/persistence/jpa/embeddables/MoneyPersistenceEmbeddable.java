package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * Persistence representation of the {@link com.acme.kampo.platform.financial.domain.model.valueObjects.Money} value object.
 *
 * <p>Maps to two columns. When a persistence entity embeds more than one
 * {@code Money} (e.g. {@code Sale} has {@code pricePerUnit} and {@code totalAmount}),
 * use {@code @AttributeOverrides} to rename the columns and avoid conflicts.</p>
 *
 * <p>Example:
 * <pre>
 * {@code @Embedded}
 * {@code @AttributeOverrides({
 *     @AttributeOverride(name = "amount",   column = @Column(name = "price_per_unit_amount")),
 *     @AttributeOverride(name = "currency", column = @Column(name = "price_per_unit_currency"))
 * })}
 * private MoneyPersistenceEmbeddable pricePerUnit;
 * </pre>
 * </p>
 */
@Setter
@Getter
@Embeddable
public class MoneyPersistenceEmbeddable {

    @Column(nullable = false, precision = 19, scale = 2)
    private BigDecimal amount;

    @Column(nullable = false, length = 3)
    private String currency;

    public MoneyPersistenceEmbeddable() {}

    public MoneyPersistenceEmbeddable(BigDecimal amount, String currency) {
        this.amount   = amount;
        this.currency = currency;
    }

}