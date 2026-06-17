package com.acme.kampo.platform.financial.interfaces.events;

import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;

/**
 * Integration event published when a new {@link Sale} is registered.
 */
public record SaleRegisteredIntegrationEvent(
        Long saleId,
        Long fundoId,
        java.math.BigDecimal totalAmount,
        String currency) {

    public static SaleRegisteredIntegrationEvent from(Sale sale) {
        return new SaleRegisteredIntegrationEvent(
                sale.getId().getValue(),
                sale.getFundoId().getValue(),
                sale.getTotalAmount().amount(),
                sale.getTotalAmount().currency());
    }
}
