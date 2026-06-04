package com.acme.kampo.platform.inventory.application.internal.commandservices;

import com.acme.kampo.platform.inventory.application.commandservices.SupplierCommandService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.command.AddSupplierCommand;
import com.acme.kampo.platform.inventory.domain.repositories.SupplierRepository;
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
    public Supplier handle(AddSupplierCommand command) {
        var supplier = new Supplier(command);
        return supplierRepository.save(supplier);
    }
}