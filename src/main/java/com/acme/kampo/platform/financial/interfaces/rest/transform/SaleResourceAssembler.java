package com.acme.kampo.platform.financial.interfaces.rest.transform;


import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.command.RegisterSaleCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateSaleCommand;
import com.acme.kampo.platform.financial.interfaces.rest.resources.CreateSaleResource;
import com.acme.kampo.platform.financial.interfaces.rest.resources.SaleResource;
import com.acme.kampo.platform.financial.interfaces.rest.resources.UpdateSaleResource;

/**
 * Assembler for Sale resource ↔ command/domain translations.
 */
public final class SaleResourceAssembler {

    private SaleResourceAssembler() {}

    public static RegisterSaleCommand toCommand(CreateSaleResource resource) {
        return new RegisterSaleCommand(
                resource.cropName(), resource.quantity(), resource.pricePerUnit(),
                resource.currency(), resource.fundoId(), resource.date());
    }

    public static UpdateSaleCommand toCommand(Long saleId, UpdateSaleResource resource) {
        return new UpdateSaleCommand(
                saleId, resource.cropName(), resource.quantity(), resource.pricePerUnit());
    }

    public static SaleResource toResource(Sale sale) {
        return new SaleResource(
                sale.getId().getValue(),
                sale.getCropName(),
                sale.getQuantity(),
                sale.getPricePerUnit().amount(),
                sale.getTotalAmount().amount(),
                sale.getTotalAmount().currency(),
                sale.getFundoId().getValue(),
                sale.getDate(),
                sale.isCancelled());
    }
}
