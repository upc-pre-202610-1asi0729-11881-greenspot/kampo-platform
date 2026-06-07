package com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.assemblers;


import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.valueObjects.Money;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.embeddables.MoneyPersistenceEmbeddable;
import com.acme.kampo.platform.financial.insfrastructure.persistence.jpa.entities.SalePersistenceEntity;

/**
 * Static assembler between the {@link Sale} aggregate and its JPA persistence representation.
 * Handles two {@link Money} fields: {@code pricePerUnit} and {@code totalAmount}.
 */
public final class SalePersistenceAssembler {

    private SalePersistenceAssembler() {}

    public static Sale toDomainFromPersistence(SalePersistenceEntity entity) {
        return new Sale(
                entity.getId(),
                entity.getCropName(),
                entity.getQuantity(),
                toDomainFromPersistence(entity.getPricePerUnit()),
                toDomainFromPersistence(entity.getTotalAmount()),
                entity.getFundoId(),
                entity.getDate(),
                entity.isCancelled());
    }

    public static SalePersistenceEntity toPersistenceFromDomain(Sale sale) {
        var entity = new SalePersistenceEntity();
        entity.setId(sale.getId() != null ? sale.getId().getValue() : null);
        entity.setCropName(sale.getCropName());
        entity.setQuantity(sale.getQuantity());
        entity.setPricePerUnit(toPersistenceFromDomain(sale.getPricePerUnit()));
        entity.setTotalAmount(toPersistenceFromDomain(sale.getTotalAmount()));
        entity.setFundoId(sale.getFundoId().getValue());
        entity.setDate(sale.getDate());
        entity.setCancelled(sale.isCancelled());
        return entity;
    }

    private static Money toDomainFromPersistence(MoneyPersistenceEmbeddable value) {
        return value == null ? null : Money.of(value.getAmount(), value.getCurrency());
    }

    private static MoneyPersistenceEmbeddable toPersistenceFromDomain(Money value) {
        return value == null ? null : new MoneyPersistenceEmbeddable(value.amount(), value.currency());
    }
}
