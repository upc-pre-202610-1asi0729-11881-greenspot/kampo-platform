package com.acme.kampo.platform.inventory.application.internal.commandservices;

import com.acme.kampo.platform.inventory.application.commandservices.SupplierCommandService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.inventory.domain.repositories.SupplierRepository;
import com.acme.kampo.platform.shared.application.result.ApplicationError;
import com.acme.kampo.platform.shared.application.result.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Internal implementation of {@link SupplierCommandService}.
 */
@Service
@Transactional
public class SupplierCommandServiceImpl implements SupplierCommandService {

    private final SupplierRepository supplierRepository;

    public SupplierCommandServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Result<Supplier, ApplicationError> handle(AddSupplierCommand command) {
        if (supplierRepository.existsByEmail(command.email())) {
            return Result.failure(ApplicationError.conflict(
                    "SUPPLIER",
                    "A supplier with email '%s' already exists".formatted(command.email())));
        }
        try {
            var supplier = supplierRepository.save(new Supplier(command));
            return Result.success(supplier);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected(
                    "SupplierCommandService.handle",
                    e.getMessage()));
        }
    }
}
