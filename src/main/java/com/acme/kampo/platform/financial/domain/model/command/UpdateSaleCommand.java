package com.acme.kampo.platform.financial.domain.model.command;


import java.math.BigDecimal;

/**
 * Command to update the mutable fields of an existing sale.
 *
 * @param saleId       ID of the sale to update
 * @param cropName     updated crop name
 * @param quantity     updated quantity — must be positive
 * @param pricePerUnit updated price per unit — must be positive
 */
public record UpdateSaleCommand(
        Long saleId,
        String cropName,
        Double quantity,
        BigDecimal pricePerUnit
) {
    public UpdateSaleCommand {
        if (saleId == null || saleId <= 0)
            throw new IllegalArgumentException("saleId must be positive");
        if (cropName == null || cropName.isBlank())
            throw new IllegalArgumentException("cropName must not be blank");
        if (quantity == null || quantity <= 0)
            throw new IllegalArgumentException("quantity must be positive");
        if (pricePerUnit == null || pricePerUnit.compareTo(BigDecimal.ZERO) <= 0)
            throw new IllegalArgumentException("pricePerUnit must be positive");
    }
}