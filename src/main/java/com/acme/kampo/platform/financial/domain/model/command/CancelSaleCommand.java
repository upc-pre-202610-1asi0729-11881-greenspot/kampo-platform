package com.acme.kampo.platform.financial.domain.model.command;

/**
 * Command to cancel an existing sale.
 * A cancelled sale is terminal — it cannot be reactivated.
 *
 * @param saleId ID of the sale to cancel
 */
public record CancelSaleCommand(Long saleId) {
    public CancelSaleCommand {
        if (saleId == null || saleId <= 0)
            throw new IllegalArgumentException("saleId must be positive");
    }
}