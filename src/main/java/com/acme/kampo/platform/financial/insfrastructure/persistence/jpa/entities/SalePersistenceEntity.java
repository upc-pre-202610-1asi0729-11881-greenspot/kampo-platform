package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities;


import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.acme.kampo.platform.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * JPA persistence entity for the {@link com.acme.kampo.platform.financial.domain.model.aggregates.Sale} aggregate.
 *
 * <p>Contains two embedded {@link MoneyPersistenceEmbeddable} fields ({@code pricePerUnit}
 * and {@code totalAmount}). Each uses distinct {@code @AttributeOverrides} to avoid
 * column name conflicts — this is the same pattern the professor uses for
 * {@code PersonName} and {@code StreetAddress} in the profiles context.</p>
 *
 * <p>Translation handled by {@link com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.SalePersistenceEntity}.</p>
 */
@Setter
@Getter
@Entity
@Table(name = "sales")
public class SalePersistenceEntity extends AuditableAbstractPersistenceEntity {

    @Column(name = "crop_name", nullable = false)
    private String cropName;

    @Column(nullable = false)
    private Double quantity;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "price_per_unit_amount",   nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "price_per_unit_currency", nullable = false, length = 3))
    })
    private MoneyPersistenceEmbeddable pricePerUnit;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount",   column = @Column(name = "total_amount_amount",   nullable = false, precision = 19, scale = 2)),
            @AttributeOverride(name = "currency", column = @Column(name = "total_amount_currency", nullable = false, length = 3))
    })
    private MoneyPersistenceEmbeddable totalAmount;

    @Column(name = "fundo_id", nullable = false)
    private Long fundoId;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean cancelled;

    public SalePersistenceEntity() {}

}
