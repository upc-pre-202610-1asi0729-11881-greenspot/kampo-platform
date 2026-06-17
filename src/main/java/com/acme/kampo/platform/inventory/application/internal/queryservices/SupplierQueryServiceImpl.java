package com.acme.kampo.platform.inventory.application.internal.queryservices;

import com.acme.kampo.platform.inventory.application.queryservices.SupplierQueryService;
import com.acme.kampo.platform.inventory.domain.model.aggregates.Supplier;
import com.acme.kampo.platform.inventory.domain.model.queries.GetAllSuppliersQuery;
import com.acme.kampo.platform.inventory.domain.model.queries.GetSupplierByIdQuery;
import com.acme.kampo.platform.inventory.domain.repositories.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Internal implementation of {@link SupplierQueryService}.
 */
@Service
@Transactional(readOnly = true)
public class SupplierQueryServiceImpl implements SupplierQueryService {

    private final SupplierRepository supplierRepository;

    public SupplierQueryServiceImpl(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    @Override
    public Optional<Supplier> handle(GetSupplierByIdQuery query) {
        return supplierRepository.findById(query.supplierId());
    }

    @Override
    public List<Supplier> handle(GetAllSuppliersQuery query) {
        return supplierRepository.findAll();
    }
}