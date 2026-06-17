package com.acme.kampo.platform.financial.application.internal.commandservices;


import com.acme.kampo.platform.financial.application.commandservices.SaleCommandService;
import com.acme.kampo.platform.financial.domain.model.aggregates.Sale;
import com.acme.kampo.platform.financial.domain.model.command.CancelSaleCommand;
import com.acme.kampo.platform.financial.domain.model.command.RegisterSaleCommand;
import com.acme.kampo.platform.financial.domain.model.command.UpdateSaleCommand;
import com.acme.kampo.platform.financial.domain.model.valueObjects.SaleId;
import com.acme.kampo.platform.financial.domain.repository.SaleRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link SaleCommandService}.
 */
@Service
@Transactional
public class SaleCommandServiceImpl implements SaleCommandService {

    private final SaleRepository saleRepository;

    public SaleCommandServiceImpl(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public Result<Sale, ApplicationError> handle(RegisterSaleCommand command) {
        try {
            var sale = saleRepository.save(new Sale(command));
            return Result.success(sale);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "SaleCommandService.handle(RegisterSaleCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Sale, ApplicationError> handle(UpdateSaleCommand command) {
        var saleOpt = saleRepository.findById(SaleId.of(command.saleId()));
        if (saleOpt.isEmpty())
            return Result.failure(ApplicationError.notFound("SALE",
                    String.valueOf(command.saleId())));
        var sale = saleOpt.get();
        if (sale.isCancelled())
            return Result.failure(ApplicationError.businessRuleViolation(
                    "SALE_CANCELLED",
                    "Cannot update sale %d — it is already cancelled".formatted(command.saleId())));
        try {
            sale.update(command);
            return Result.success(saleRepository.save(sale));
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "SaleCommandService.handle(UpdateSaleCommand)", e.getMessage()));
        }
    }

    @Override
    public Result<Void, ApplicationError> handle(CancelSaleCommand command) {
        var saleOpt = saleRepository.findById(SaleId.of(command.saleId()));
        if (saleOpt.isEmpty())
            return Result.failure(ApplicationError.notFound("SALE",
                    String.valueOf(command.saleId())));
        var sale = saleOpt.get();
        if (sale.isCancelled())
            return Result.failure(ApplicationError.businessRuleViolation(
                    "SALE_ALREADY_CANCELLED",
                    "Sale %d is already cancelled".formatted(command.saleId())));
        try {
            sale.cancel();
            saleRepository.save(sale);
            return Result.success(null);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "SaleCommandService.handle(CancelSaleCommand)", e.getMessage()));
        }
    }
}